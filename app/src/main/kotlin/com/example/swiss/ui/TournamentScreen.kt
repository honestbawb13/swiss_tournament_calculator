package com.example.swiss.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.example.swiss.export.CsvExporter

@Composable
fun TournamentScreen(id: String, onManualPair: () -> Unit, vm: TournamentViewModel = hiltViewModel()) {
    val ui by vm.ui.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Tournament: ${ui.name}")
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            AssistChip(onClick = vm::toggleLock, label = { Text(if (ui.isLocked) "Unlock" else "Lock") })
            Button(onClick = vm::generateNextRound, enabled = ui.state != null && ui.canGenerateNextRound) { Text("Generate Next Round") }
            ExportButtons(ui)
            if (!ui.isLocked) {
                OutlinedButton(onClick = onManualPair, enabled = ui.state != null && ui.canGenerateNextRound) { Text("Manual Pairing") }
            }
        }
        Divider(Modifier.padding(vertical = 8.dp))
        if (ui.state == null) {
            Text("Loading...")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(ui.rounds) { rUi ->
                    Text("Round ${rUi.round.index + 1}")
                    rUi.round.matches.forEach { m ->
                        val idToName = ui.state!!.players.associate { it.id.value to it.name }
                        MatchEditor(
                            isLocked = ui.isLocked,
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
                    Divider()
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
    Column {
        Text("$home vs $away")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Counter("Home W", h, enabled = !isLocked) { h = (h + it).coerceAtLeast(0) }
            Counter("Away W", a, enabled = !isLocked) { a = (a + it).coerceAtLeast(0) }
            Counter("Draws", d, enabled = !isLocked) { d = (d + it).coerceAtLeast(0) }
            val total = h + a + d
            val valid = when (bestOf) {
                com.example.engine.model.BestOf.BO1 -> total == 1 && (allowDraws || d == 0)
                com.example.engine.model.BestOf.BO3 -> total in 2..3 && (allowDraws || d == 0)
            }
            Button(onClick = { onChange(h, a, d) }, enabled = !isLocked && valid) { Text(if (valid) "Save" else "Invalid") }
        }
    }
}

@Composable
private fun Counter(label: String, value: Int, enabled: Boolean, onDelta: (Int) -> Unit) {
    Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(label)
        OutlinedButton(onClick = { onDelta(-1) }, enabled = enabled) { Text("-") }
        Text(value.toString())
        OutlinedButton(onClick = { onDelta(1) }, enabled = enabled) { Text("+") }
    }
}

@Composable
private fun ExportButtons(ui: TournamentUiState) {
    val context = LocalContext.current
    val exporter = remember { CsvExporter() }

    val standingsLauncher = rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument("text/csv")) { uri ->
        if (uri != null && ui.state != null) {
            val csv = exporter.standingsCsv(ui.state, ui.name)
            writeTextToUri(context, uri, csv)
        }
    }
    val matchesLauncher = rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument("text/csv")) { uri ->
        if (uri != null && ui.state != null) {
            val csv = exporter.matchesCsv(ui.state, ui.name)
            writeTextToUri(context, uri, csv)
        }
    }

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedButton(onClick = { standingsLauncher.launch("standings_${ui.name}.csv") }, enabled = ui.state != null) { Text("Export Standings") }
        OutlinedButton(onClick = { matchesLauncher.launch("matches_${ui.name}.csv") }, enabled = ui.state != null) { Text("Export Matches") }
    }
}

private fun writeTextToUri(context: Context, uri: android.net.Uri, text: String) {
    context.contentResolver.openOutputStream(uri)?.use { os ->
        os.writer(Charsets.UTF_8).use { it.write(text) }
    }
}
