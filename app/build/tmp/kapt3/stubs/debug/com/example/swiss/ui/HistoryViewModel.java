package com.example.swiss.ui;

import androidx.compose.runtime.Composable;
import androidx.compose.ui.Modifier;
import com.example.swiss.data.TournamentEntity;
import com.example.swiss.data.TournamentRepository;
import kotlinx.coroutines.Dispatchers;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\n\u001a\u00020\u000bR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/example/swiss/ui/HistoryViewModel;", "Landroidx/lifecycle/ViewModel;", "repo", "Lcom/example/swiss/data/TournamentRepository;", "(Lcom/example/swiss/data/TournamentRepository;)V", "items", "Landroidx/compose/runtime/snapshots/SnapshotStateList;", "Lcom/example/swiss/data/TournamentEntity;", "getItems", "()Landroidx/compose/runtime/snapshots/SnapshotStateList;", "refresh", "", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class HistoryViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.swiss.data.TournamentRepository repo = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.snapshots.SnapshotStateList<com.example.swiss.data.TournamentEntity> items = null;
    
    @javax.inject.Inject()
    public HistoryViewModel(@org.jetbrains.annotations.NotNull()
    com.example.swiss.data.TournamentRepository repo) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.runtime.snapshots.SnapshotStateList<com.example.swiss.data.TournamentEntity> getItems() {
        return null;
    }
    
    public final void refresh() {
    }
}