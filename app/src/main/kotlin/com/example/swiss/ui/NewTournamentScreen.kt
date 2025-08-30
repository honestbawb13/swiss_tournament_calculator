package com.example.swiss.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.engine.model.BestOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTournamentScreen(onDone: (String) -> Unit, vm: NewTournamentViewModel = hiltViewModel()) {
    val state by vm.state.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = state.name,
            onValueChange = vm::updateName,
            label = { Text("Tournament Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = state.playersRaw,
            onValueChange = vm::updatePlayers,
            label = { Text("Players (one per line)") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 6
        )
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            var expanded by remember { mutableStateOf(false) }
            Button(onClick = { expanded = true }) { Text("${if (state.bestOf == BestOf.BO1) "Best of 1" else "Best of 3"}") }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(text = { Text("Best of 1") }, onClick = { vm.updateBestOf(BestOf.BO1); expanded = false })
                DropdownMenuItem(text = { Text("Best of 3") }, onClick = { vm.updateBestOf(BestOf.BO3); expanded = false })
            }

            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                Text("Random R1")
                Switch(checked = state.randomFirstRound, onCheckedChange = vm::updateRandomFirst)
            }
        }
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("Rounds: ${state.rounds}")
            Button(onClick = { vm.updateRounds(state.rounds + 1) }) { Text("+") }
            Button(onClick = { vm.updateRounds(state.rounds - 1) }) { Text("-") }
        }
        Spacer(Modifier.height(12.dp))
        if (state.error != null) Text(state.error!!, color = MaterialTheme.colorScheme.error)
        Button(onClick = { vm.createTournamentAndSeed(onDone) }, enabled = !state.isCreating && vm.canCreate()) {
            Text(if (state.isCreating) "Creating..." else "Create")
        }
    }
}
