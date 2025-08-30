package com.example.swiss.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.example.swiss.export.CsvExporter
import com.example.engine.SwissEngine
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.outlined.Info
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun TournamentScreen(id: String, onManualPair: () -> Unit, vm: TournamentViewModel = hiltViewModel()) {
    val ui by vm.ui.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Tournament: ${ui.name}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = vm::generateNextRound, enabled = ui.state != null && ui.canGenerateNextRound) { Text("Next Round") }
            ExportButtons(ui)
            OutlinedButton(onClick = onManualPair, enabled = ui.state != null && ui.canGenerateNextRound) { Text("Manual Pairing") }
        }
        Divider(Modifier.padding(vertical = 8.dp))

        val roundCount = ui.rounds.size
        val selectedTab = rememberSaveable(roundCount) { mutableStateOf(if (roundCount == 0) 0 else roundCount - 1) }
        ScrollableTabRow(selectedTabIndex = selectedTab.value) {
            ui.rounds.forEach { rUi ->
                val idx = rUi.round.index
                Tab(selected = selectedTab.value == idx, onClick = { selectedTab.value = idx }, text = { Text("R${idx + 1}") })
            }
            val standingsIndex = roundCount
            Tab(selected = selectedTab.value == standingsIndex, onClick = { selectedTab.value = standingsIndex }, text = { Text("Standings") })
        }
        Spacer(Modifier.height(8.dp))
        if (ui.state == null) {
            Text("Loading...")
        } else {
            val standingsIndex = ui.rounds.size
            if (selectedTab.value == standingsIndex) {
                StandingsView(state = ui.state!!, unlockedRounds = ui.unlockedRounds)
            } else {
                val ridx = selectedTab.value
                val rUi = ui.rounds.firstOrNull { it.round.index == ridx }
                if (rUi != null) {
                    val latestIndex = ui.rounds.maxByOrNull { it.round.index }?.round?.index
                    val isLatest = ridx == latestIndex
                    val complete = remember(rUi.round, ui.state!!.config) { roundCompleteUi(rUi.round, ui.state!!) }

                    // Progress graphic
                    val totalMatches = rUi.round.matches.count { it.away != null && it.home != null } // ignore bye for progress
                    val completedMatches = rUi.round.matches.count { it.result != null && it.away != null && it.home != null }
                    val progress = if (totalMatches == 0) 0f else completedMatches.toFloat() / totalMatches.toFloat()
                    Column {
                        LinearProgressIndicator(progress = progress, modifier = Modifier.fillMaxWidth())
                        Text("Round ${ridx + 1}: $completedMatches/$totalMatches matches saved")
                    }

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        AssistChip(onClick = {}, enabled = false, label = { Text(if (complete) "Completed" else "Incomplete") })
                        if (!isLatest) {
                            val unlocked = ui.unlockedRounds.contains(ridx)
                            var showConfirm by remember { mutableStateOf(false) }
                            if (!unlocked) {
                                OutlinedButton(onClick = { showConfirm = true }) { Text("Unlock Round") }
                            } else {
                                AssistChip(onClick = {}, enabled = false, label = { Text("Unlocked") })
                            }
                            if (showConfirm) {
                                AlertDialog(
                                    onDismissRequest = { showConfirm = false },
                                    title = { Text("Unlock Round ${ridx + 1}?") },
                                    text = { Text("This cannot be undone. Changes will be logged in export and standings.") },
                                    confirmButton = { TextButton(onClick = { vm.unlockRound(ridx) { showConfirm = false } }) { Text("Confirm") } },
                                    dismissButton = { TextButton(onClick = { showConfirm = false }) { Text("Cancel") } }
                                )
                            }
                        }
                    }

                    // Matches list
                    val idToName = ui.state!!.players.associate { it.id.value to it.name }
                    rUi.round.matches.forEach { m ->
                        val editable = isLatest || ui.unlockedRounds.contains(ridx)
                        MatchEditor(
                            isLocked = !editable,
                            matchId = m.id.value,
                            roundIndex = rUi.round.index,
                            home = m.home?.value?.let { idToName[it] } ?: "BYE",
                            away = m.away?.value?.let { idToName[it] } ?: "BYE",
                            initHome = m.result?.homeGamesWon ?: 0,
                            initAway = m.result?.awayGamesWon ?: 0,
                            initDraws = m.result?.draws ?: 0,
                            bestOf = ui.state!!.config.bestOf,
                            allowDraws = ui.state!!.config.allowDraws,
                            onChange = { h, a, d -> vm.updateMatchResult(rUi.round.index, m.id.value, h, a, d) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MatchEditor(
    isLocked: Boolean,
    matchId: String,
    roundIndex: Int,
    home: String,
    away: String,
    initHome: Int,
    initAway: Int,
    initDraws: Int,
    bestOf: com.example.engine.model.BestOf,
    allowDraws: Boolean,
    onChange: (Int, Int, Int) -> Unit,
) {
    var h by remember(matchId) { mutableStateOf(initHome) }
    var a by remember(matchId) { mutableStateOf(initAway) }
    var d by remember(matchId) { mutableStateOf(initDraws) }
    val valid = when (bestOf) {
        com.example.engine.model.BestOf.BO1 -> {
            val total = h + a + d
            total == 1 && (allowDraws || d == 0)
        }
        com.example.engine.model.BestOf.BO3 -> {
            val total = h + a + d
            total in 2..3 && (allowDraws || d == 0)
        }
    }
    ElevatedCard(shape = RoundedCornerShape(12.dp)) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("$home vs $away", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Stepper(label = "$home wins", value = h, enabled = !isLocked) { delta -> h = (h + delta).coerceAtLeast(0) }
                Stepper(label = "$away wins", value = a, enabled = !isLocked) { delta -> a = (a + delta).coerceAtLeast(0) }
            }
            Spacer(Modifier.height(8.dp))
            Stepper(label = "Draws", value = d, enabled = !isLocked && allowDraws) { delta -> d = (d + delta).coerceAtLeast(0) }
            Spacer(Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                val msg = if (isLocked) "Locked" else if (!valid) {
                    if (bestOf == com.example.engine.model.BestOf.BO1) "Pick exactly one game"
                    else "Pick 2–3 total games"
                } else "Ready to save"
                Text(msg, color = if (valid && !isLocked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant)
                FilledTonalButton(onClick = { onChange(h, a, d) }, enabled = !isLocked && valid) {
                    Icon(Icons.Default.Save, contentDescription = null)
                    Spacer(Modifier.width(6.dp))
                    Text("Save")
                }
            }
        }
    }
}

@Composable
private fun Stepper(label: String, value: Int, enabled: Boolean, onDelta: (Int) -> Unit) {
    Column {
        Text(label)
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            IconButton(onClick = { onDelta(-1) }, enabled = enabled) { Icon(Icons.Filled.Remove, contentDescription = "Decrement") }
            Text(value.toString(), textAlign = TextAlign.Center, modifier = Modifier.width(24.dp))
            IconButton(onClick = { onDelta(1) }, enabled = enabled) { Icon(Icons.Filled.Add, contentDescription = "Increment") }
        }
    }
}

@Composable
private fun ExportButtons(ui: TournamentUiState) {
    val context = LocalContext.current
    val exporter = remember { CsvExporter() }

    val standingsLauncher = rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument("text/csv")) { uri ->
        if (uri != null && ui.state != null) {
            val csv = exporter.standingsCsv(ui.state, ui.name, ui.unlockedRounds)
            writeTextToUri(context, uri, csv)
        }
    }
    val matchesLauncher = rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument("text/csv")) { uri ->
        if (uri != null && ui.state != null) {
            val csv = exporter.matchesCsv(ui.state, ui.name, ui.unlockedRounds)
            writeTextToUri(context, uri, csv)
        }
    }

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedButton(onClick = { standingsLauncher.launch("standings_${ui.name}.csv") }, enabled = ui.state != null) { Text("Standings CSV") }
        OutlinedButton(onClick = { matchesLauncher.launch("matches_${ui.name}.csv") }, enabled = ui.state != null) { Text("Matches CSV") }
    }
}

private fun writeTextToUri(context: Context, uri: android.net.Uri, text: String) {
    context.contentResolver.openOutputStream(uri)?.use { os ->
        os.writer(Charsets.UTF_8).use { it.write(text) }
    }
}

@Composable
private fun StandingsView(state: com.example.engine.model.TournamentState, unlockedRounds: Set<Int>) {
    val engine = remember { SwissEngine() }
    val standings = remember(state) { engine.standings(state) }
    val scrollState = rememberScrollState()
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        if (unlockedRounds.isNotEmpty()) {
            AssistChip(onClick = {}, enabled = false, label = { Text("Unlocked rounds: ${unlockedRounds.sorted().joinToString(", ") { (it + 1).toString() }}") })
        }
        Row(modifier = Modifier
            .horizontalScroll(scrollState)
            .fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Column(modifier = Modifier.width(56.dp)) {
                Text("Rank", fontWeight = FontWeight.SemiBold)
                Divider()
                standings.forEachIndexed { idx, _ -> Text("${idx + 1}") }
            }
            Column(modifier = Modifier.width(160.dp)) {
                Text("Player", fontWeight = FontWeight.SemiBold)
                Divider()
                standings.forEach { s -> Text(s.player.name) }
            }
            Column(modifier = Modifier.width(64.dp)) {
                Text("MP", fontWeight = FontWeight.SemiBold)
                Divider()
                standings.forEach { s -> Text("${s.matchPoints}") }
            }
            Column(modifier = Modifier.width(80.dp)) {
                Text("OMW%", fontWeight = FontWeight.SemiBold)
                Divider()
                standings.forEach { s -> Text(String.format("%.3f", s.opponentsMatchWinPct)) }
            }
            Column(modifier = Modifier.width(80.dp)) {
                Text("GWP%", fontWeight = FontWeight.SemiBold)
                Divider()
                standings.forEach { s -> Text(String.format("%.3f", s.gameWinPct)) }
            }
            Column(modifier = Modifier.width(64.dp)) {
                Text("SB", fontWeight = FontWeight.SemiBold)
                Divider()
                standings.forEach { s -> Text("${s.sonnebornBerger}") }
            }
        }
    }
}

private fun roundCompleteUi(round: com.example.engine.model.Round, state: com.example.engine.model.TournamentState): Boolean {
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
