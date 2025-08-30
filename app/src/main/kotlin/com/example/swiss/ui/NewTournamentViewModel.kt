package com.example.swiss.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.engine.model.BestOf
import com.example.engine.model.RoundSuggester
import com.example.swiss.data.TournamentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NewTournamentState(
    val name: String = "",
    val playersRaw: String = "",
    val bestOf: BestOf = BestOf.BO3,
    val randomFirstRound: Boolean = true,
    val rounds: Int = 0,
    val isCreating: Boolean = false,
    val error: String? = null,
)

@HiltViewModel
class NewTournamentViewModel @Inject constructor(
    private val repo: TournamentRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(NewTournamentState())
    val state: StateFlow<NewTournamentState> = _state

    fun updateName(v: String) { _state.value = _state.value.copy(name = v) }
    fun updatePlayers(v: String) {
        _state.value = _state.value.copy(playersRaw = v)
        recomputeRounds()
    }
    fun updateBestOf(v: BestOf) { _state.value = _state.value.copy(bestOf = v) }
    fun updateRandomFirst(v: Boolean) { _state.value = _state.value.copy(randomFirstRound = v) }
    fun updateRounds(v: Int) { _state.value = _state.value.copy(rounds = v.coerceAtLeast(1)) }

    private fun recomputeRounds() {
        val players = playersList().size
        if (players >= 4) {
            _state.value = _state.value.copy(rounds = RoundSuggester.suggestedRounds(players))
        }
    }

    private fun playersList(): List<String> = _state.value.playersRaw
        .split('\n')
        .map { it.trim() }
        .filter { it.isNotEmpty() }

    fun canCreate(): Boolean = _state.value.name.isNotBlank() && playersList().size in 4..64

    fun createTournamentAndSeed(onCreated: (String) -> Unit) {
        if (!canCreate()) {
            _state.value = _state.value.copy(error = "Enter name and 4–64 players")
            return
        }
        val s = _state.value
        viewModelScope.launch {
            _state.value = s.copy(isCreating = true, error = null)
            try {
                val tid = repo.createTournament(
                    name = s.name.trim(),
                    playerNames = playersList(),
                    bestOf = s.bestOf,
                    allowDraws = true,
                    roundsPlanned = s.rounds.coerceAtLeast(1),
                    randomFirstRound = s.randomFirstRound,
                    locked = true,
                )
                // Load state, generate R1 if configured random, and persist it
                val state = repo.loadTournamentState(tid)
                if (state != null && state.config.randomFirstRound) {
                    val engine = com.example.engine.SwissEngine()
                    val r = engine.generateNextRound(state)
                    repo.saveRound(tid, r)
                }
                onCreated(tid)
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message ?: "Failed to create")
            } finally {
                _state.value = _state.value.copy(isCreating = false)
            }
        }
    }
}

