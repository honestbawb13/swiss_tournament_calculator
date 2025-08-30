package com.example.swiss.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ManualPairingScreen(onDone: () -> Unit, vm: ManualPairingViewModel = hiltViewModel()) {
    val ui by vm.ui.collectAsState()
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Manual Pairing")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = vm::addPair, enabled = ui.selected.size == 2) { Text("Add Pair") }
            OutlinedButton(onClick = vm::clearLastPair, enabled = ui.pairs.isNotEmpty()) { Text("Undo Pair") }
            Button(onClick = { vm.save(onDone) }, enabled = vm.canSave()) { Text("Save Round") }
        }
        Spacer(Modifier.height(8.dp))
        val bye = ui.bye
        if (ui.state?.players?.size?.rem(2) == 1 && bye == null) {
            Text("Choose a bye: tap a player below")
        } else if (bye != null) {
            AssistChip(onClick = {}, label = { Text("Bye: ${bye.name}") })
        }
        Divider(Modifier.padding(vertical = 8.dp))
        Text("Unpaired Players")
        LazyColumn(modifier = Modifier.weight(1f, fill = true)) {
            items(ui.unpaired, key = { it.id.value }) { p ->
                val selected = ui.selected.any { it.id == p.id }
                ListItem(
                    headlineContent = { Text(p.name) },
                    supportingContent = { if (selected) Text("Selected") },
                    modifier = Modifier.fillMaxWidth().clickable {
                        if (ui.state?.players?.size?.rem(2) == 1 && ui.bye == null && ui.selected.isEmpty()) {
                            vm.setBye(p)
                        } else {
                            vm.toggleSelect(p)
                        }
                    }
                )
                Divider()
            }
        }
        Spacer(Modifier.height(8.dp))
        Text("Current Pairs")
        ui.pairs.forEach { (a, b) -> Text("- ${a.name} vs ${b.name}") }
    }
}
