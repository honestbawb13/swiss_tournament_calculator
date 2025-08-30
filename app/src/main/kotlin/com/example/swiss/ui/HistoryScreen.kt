package com.example.swiss.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.swiss.data.TournamentEntity
import com.example.swiss.data.TournamentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repo: TournamentRepository,
) : ViewModel() {
    val items = mutableStateListOf<TournamentEntity>()
    fun refresh() {
        viewModelScope.launch {
            val list = repo.listTournaments()
            items.clear(); items.addAll(list)
        }
    }
}

@Composable
fun HistoryScreen(onOpen: (String) -> Unit, vm: HistoryViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) { vm.refresh() }
    LazyColumn {
        items(vm.items, key = { it.id }) { t ->
            Text(t.name, modifier = Modifier.fillMaxWidth().clickable { onOpen(t.id) })
            Divider()
        }
    }
}
