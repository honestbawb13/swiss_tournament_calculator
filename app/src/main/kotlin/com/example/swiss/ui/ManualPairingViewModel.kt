package com.example.swiss.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.engine.model.Match
import com.example.engine.model.MatchId
import com.example.engine.model.MatchResult
import com.example.engine.model.Player
import com.example.engine.model.PlayerId
import com.example.engine.model.Round
import com.example.engine.model.TournamentState
import com.example.swiss.data.TournamentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

data class ManualPairingState(
    val tid: String,
    val state: TournamentState? = null,
    val unpaired: List<Player> = emptyList(),
    val pairs: List<Pair<Player, Player>> = emptyList(),
    val selected: List<Player> = emptyList(),
    val bye: Player? = null,
    val error: String? = null,
)

@HiltViewModel
class ManualPairingViewModel @Inject constructor(
    private val repo: TournamentRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val tid: String = savedStateHandle.get<String>("id") ?: ""
    private val _ui = MutableStateFlow(ManualPairingState(tid = tid))
    val ui: StateFlow<ManualPairingState> = _ui

    init { refresh() }

    fun refresh() {
        viewModelScope.launch {
            val st = repo.loadTournamentState(tid)
            if (st != null) {
                _ui.value = _ui.value.copy(state = st, unpaired = st.players.sortedBy { it.name }, pairs = emptyList(), selected = emptyList(), bye = null)
            }
        }
    }

    fun toggleSelect(p: Player) {
        val cur = _ui.value.selected.toMutableList()
        if (cur.any { it.id == p.id }) cur.removeAll { it.id == p.id } else cur.add(p)
        _ui.value = _ui.value.copy(selected = cur)
    }

    fun addPair() {
        val sel = _ui.value.selected
        if (sel.size != 2) return
        val (a, b) = sel
        val pairs = _ui.value.pairs + (a to b)
        val remaining = _ui.value.unpaired.filter { it.id != a.id && it.id != b.id }
        _ui.value = _ui.value.copy(pairs = pairs, unpaired = remaining, selected = emptyList())
    }

    fun setBye(p: Player) {
        // Only if odd count
        _ui.value = _ui.value.copy(bye = p, unpaired = _ui.value.unpaired.filter { it.id != p.id }, selected = _ui.value.selected.filter { it.id != p.id })
    }

    fun clearLastPair() {
        val pairs = _ui.value.pairs.toMutableList()
        if (pairs.isEmpty()) return
        val last = pairs.removeLast()
        val unpaired = (_ui.value.unpaired + listOf(last.first, last.second)).sortedBy { it.name }
        _ui.value = _ui.value.copy(pairs = pairs, unpaired = unpaired)
    }

    fun canSave(): Boolean {
        val st = _ui.value.state ?: return false
        val total = st.players.size
        val pairedCount = _ui.value.pairs.size * 2 + (_ui.value.bye?.let { 1 } ?: 0)
        return pairedCount == total
    }

    fun save(onSaved: () -> Unit) {
        val st = _ui.value.state ?: return
        val latestIndex = st.rounds.maxByOrNull { it.index }?.index ?: 0
        val latest = st.rounds.firstOrNull { it.index == latestIndex }
        val latestEditable = latest == null || latest.matches.none { it.result != null && it.home != null && it.away != null }
        val targetIndex = if (latestEditable) latestIndex else st.rounds.size
        val matches = mutableListOf<Match>()
        _ui.value.pairs.forEachIndexed { i, (a, b) ->
            matches += Match(
                id = MatchId("r${targetIndex + 1}_m${i}_${UUID.randomUUID().toString().take(8)}"),
                roundIndex = targetIndex,
                home = a.id,
                away = b.id,
                result = null,
            )
        }
        _ui.value.bye?.let { byePlayer ->
            matches += Match(
                id = MatchId("r${targetIndex + 1}_bye"),
                roundIndex = targetIndex,
                home = byePlayer.id,
                away = null,
                result = MatchResult(1, 0, 0), // award bye win
            )
        }
        viewModelScope.launch {
            val round = Round(index = targetIndex, matches = matches)
            if (latestEditable) repo.replaceRound(tid, round) else repo.saveRound(tid, round)
            onSaved()
        }
    }
}
