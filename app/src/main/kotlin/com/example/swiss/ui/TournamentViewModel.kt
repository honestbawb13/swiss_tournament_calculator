package com.example.swiss.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.engine.SwissEngine
import com.example.engine.model.Match
import com.example.engine.model.MatchId
import com.example.engine.model.MatchResult
import com.example.engine.model.Round
import com.example.engine.model.TournamentState
import com.example.swiss.data.TournamentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RoundUi(
    val round: Round,
)

data class TournamentUiState(
    val tid: String,
    val name: String = "",
    val state: TournamentState? = null,
    val rounds: List<RoundUi> = emptyList(),
    val isLocked: Boolean = true,
    val canGenerateNextRound: Boolean = false,
    val error: String? = null,
)

@HiltViewModel
class TournamentViewModel @Inject constructor(
    private val repo: TournamentRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val tid: String = savedStateHandle.get<String>("id") ?: ""
    private val engine = SwissEngine()

    private val _ui = MutableStateFlow(TournamentUiState(tid = tid))
    val ui: StateFlow<TournamentUiState> = _ui

    init { refresh() }

    fun refresh() {
        viewModelScope.launch {
            try {
                val t = repo.loadTournamentState(tid)
                val meta = repo.getTournamentMeta(tid)
                if (t != null) {
                    val latest = t.rounds.maxByOrNull { it.index }
                    val canGen = latest?.let { roundComplete(it, t) } ?: true
                    _ui.value = _ui.value.copy(
                        state = t,
                        isLocked = t.locked,
                        rounds = t.rounds.map { RoundUi(it) },
                        name = meta?.name ?: tid,
                        canGenerateNextRound = canGen,
                    )
                }
            } catch (e: Exception) {
                _ui.value = _ui.value.copy(error = e.message)
            }
        }
    }

    fun toggleLock() {
        viewModelScope.launch {
            val locked = !(_ui.value.isLocked)
            repo.setLocked(tid, locked)
            refresh()
        }
    }

    fun generateNextRound() {
        val st = _ui.value.state ?: return
        if (!_ui.value.canGenerateNextRound) return
        viewModelScope.launch {
            val r = engine.generateNextRound(st)
            repo.saveRound(tid, r)
            refresh()
        }
    }

    fun updateMatchResult(roundIndex: Int, matchId: String, homeWins: Int, awayWins: Int, draws: Int) {
        val st = _ui.value.state ?: return
        if (_ui.value.isLocked) return
        val round = st.rounds.firstOrNull { it.index == roundIndex } ?: return
        val updated = round.copy(matches = round.matches.map { m ->
            if (m.id.value == matchId) m.copy(result = MatchResult(homeWins, awayWins, draws)) else m
        })
        viewModelScope.launch {
            repo.saveRound(tid, updated)
            refresh()
        }
    }
}

private fun roundComplete(round: Round, state: TournamentState): Boolean {
    val bestOf = state.config.bestOf
    val allowDraws = state.config.allowDraws
    return round.matches.all { m ->
        val r = m.result ?: return@all false
        val total = r.homeGamesWon + r.awayGamesWon + r.draws
        when (bestOf) {
            com.example.engine.model.BestOf.BO1 -> total == 1 && (allowDraws || r.draws == 0)
            com.example.engine.model.BestOf.BO3 -> total in 2..3 && (allowDraws || r.draws == 0)
        }
    }
}
