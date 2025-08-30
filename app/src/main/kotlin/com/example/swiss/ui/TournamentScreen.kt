package com.example.swiss.ui

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.engine.SwissEngine
import com.example.swiss.export.CsvExporter

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun TournamentScreen(id: String, onManualPair: () -> Unit, vm: TournamentViewModel = hiltViewModel()) {
    val ui by vm.ui.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(ui.name) }, actions = { ExportMenuButton(ui) })
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            val st = ui.state
            if (st != null) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    AssistChip(onClick = {}, enabled = false, label = { Text("Players: ${st.players.size}") })
                    AssistChip(onClick = {}, enabled = false, label = { Text("Best: ${st.config.bestOf.name}") })
                    AssistChip(onClick = {}, enabled = false, label = { Text("Planned: ${st.config.roundsPlanned}") })
                }
            }
            Spacer(Modifier.height(8.dp))
            // Actions
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = vm::generateNextRound, enabled = ui.state != null && ui.canGenerateNextRound) { Text("Next Round") }
                val latestIdx = ui.rounds.maxByOrNull { it.round.index }?.round?.index
                val latest = ui.rounds.firstOrNull { it.round.index == latestIdx }
                val latestSaved = latest?.round?.matches?.count { it.result != null && it.home != null && it.away != null } ?: 0
                val latestTotal = latest?.round?.matches?.count { it.home != null && it.away != null } ?: 0
                val canManualPair = latestIdx != null && latestSaved == 0 && latestTotal > 0
                OutlinedButton(onClick = onManualPair, enabled = canManualPair) { Text("Manual Pairing") }
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

                        val totalMatches = rUi.round.matches.count { it.away != null && it.home != null }
                        val completedMatches = rUi.round.matches.count { it.result != null && it.away != null && it.home != null }
                        val progress by animateFloatAsState(
                            targetValue = if (totalMatches == 0) 0f else completedMatches.toFloat() / totalMatches.toFloat(),
                            animationSpec = tween(durationMillis = 600, easing = LinearOutSlowInEasing),
                            label = "roundProgress"
                        )
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            CircularProgressIndicator(progress = progress, strokeWidth = 6.dp)
                            Text("Round ${ridx + 1}: $completedMatches/$totalMatches matches saved")
                            Spacer(Modifier.weight(1f))
                            AnimatedContent(targetState = complete, transitionSpec = { fadeIn() with fadeOut() }, label = "completeChip") { isComplete ->
                                AssistChip(onClick = {}, enabled = false, label = { Text(if (isComplete) "Completed" else "Incomplete") })
                            }
                            if (!isLatest) {
                                val unlocked = ui.unlockedRounds.contains(ridx)
                                var showConfirm by remember { mutableStateOf(false) }
                                if (!unlocked) {
                                    OutlinedButton(onClick = { showConfirm = true }) { Text("Unlock") }
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

                        val idToName = ui.state!!.players.associate { it.id.value to it.name }
                        LazyColumn(modifier = Modifier.fillMaxWidth().weight(1f, fill = true), verticalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(vertical = 8.dp)) {
                            items(rUi.round.matches.size) { mi ->
                                val m = rUi.round.matches[mi]
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
                        if (completedMatches == 0) {
                            PlaceholderGraphic()
                        }
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
                    if (bestOf == com.example.engine.model.BestOf.BO1) "Pick exactly one game" else "Pick 2-3 total games"
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
            IconButton(onClick = { onDelta(-1) }, enabled = enabled) { Icon(Icons.Filled.Remove, contentDescription = "Decrement $label") }
            Text(value.toString(), textAlign = TextAlign.Center, modifier = Modifier.width(24.dp))
            IconButton(onClick = { onDelta(1) }, enabled = enabled) { Icon(Icons.Filled.Add, contentDescription = "Increment $label") }
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
        OutlinedButton(onClick = { standingsLauncher.launch("standings_${ui.name}.csv") }, enabled = ui.state != null) { Text("Standings", maxLines = 1) }
        OutlinedButton(onClick = { matchesLauncher.launch("matches_${ui.name}.csv") }, enabled = ui.state != null) { Text("Matches", maxLines = 1) }
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
        Row(
            modifier = Modifier.horizontalScroll(scrollState).fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
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

@Composable
private fun ExportMenuButton(ui: TournamentUiState) {
    val context = LocalContext.current
    val exporter = remember { CsvExporter() }
    var expanded by remember { mutableStateOf(false) }
    val standingsLauncher = rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument("text/csv")) { uri ->
        expanded = false
        if (uri != null && ui.state != null) {
            val csv = exporter.standingsCsv(ui.state, ui.name, ui.unlockedRounds)
            writeTextToUri(context, uri, csv)
        }
    }
    val matchesLauncher = rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument("text/csv")) { uri ->
        expanded = false
        if (uri != null && ui.state != null) {
            val csv = exporter.matchesCsv(ui.state, ui.name, ui.unlockedRounds)
            writeTextToUri(context, uri, csv)
        }
    }
    Box {
        IconButton(onClick = { expanded = true }) { Icon(Icons.Filled.MoreVert, contentDescription = "More actions") }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(text = { Text("Export Standings") }, onClick = { standingsLauncher.launch("standings_${ui.name}.csv") })
            DropdownMenuItem(text = { Text("Export Matches") }, onClick = { matchesLauncher.launch("matches_${ui.name}.csv") })
        }
    }
}

@Composable
private fun PlaceholderGraphic() {
    Surface(tonalElevation = 2.dp, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(Icons.Filled.SportsEsports, contentDescription = null)
            Text("No results yet — start saving match results to see progress.")
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
