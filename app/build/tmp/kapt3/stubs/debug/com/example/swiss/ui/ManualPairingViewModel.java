package com.example.swiss.ui;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.example.engine.model.Match;
import com.example.engine.model.MatchId;
import com.example.engine.model.MatchResult;
import com.example.engine.model.Player;
import com.example.engine.model.PlayerId;
import com.example.engine.model.Round;
import com.example.engine.model.TournamentState;
import com.example.swiss.data.TournamentRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import java.util.UUID;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u0010\u001a\u00020\u0011J\u0006\u0010\u0012\u001a\u00020\u0013J\u0006\u0010\u0014\u001a\u00020\u0011J\u0006\u0010\u0015\u001a\u00020\u0011J\u0014\u0010\u0016\u001a\u00020\u00112\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00110\u0018J\u000e\u0010\u0019\u001a\u00020\u00112\u0006\u0010\u001a\u001a\u00020\u001bJ\u000e\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u001a\u001a\u00020\u001bR\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u001d"}, d2 = {"Lcom/example/swiss/ui/ManualPairingViewModel;", "Landroidx/lifecycle/ViewModel;", "repo", "Lcom/example/swiss/data/TournamentRepository;", "savedStateHandle", "Landroidx/lifecycle/SavedStateHandle;", "(Lcom/example/swiss/data/TournamentRepository;Landroidx/lifecycle/SavedStateHandle;)V", "_ui", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/example/swiss/ui/ManualPairingState;", "tid", "", "ui", "Lkotlinx/coroutines/flow/StateFlow;", "getUi", "()Lkotlinx/coroutines/flow/StateFlow;", "addPair", "", "canSave", "", "clearLastPair", "refresh", "save", "onSaved", "Lkotlin/Function0;", "setBye", "p", "Lcom/example/engine/model/Player;", "toggleSelect", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ManualPairingViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.swiss.data.TournamentRepository repo = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String tid = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.swiss.ui.ManualPairingState> _ui = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.swiss.ui.ManualPairingState> ui = null;
    
    @javax.inject.Inject()
    public ManualPairingViewModel(@org.jetbrains.annotations.NotNull()
    com.example.swiss.data.TournamentRepository repo, @org.jetbrains.annotations.NotNull()
    androidx.lifecycle.SavedStateHandle savedStateHandle) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.swiss.ui.ManualPairingState> getUi() {
        return null;
    }
    
    public final void refresh() {
    }
    
    public final void toggleSelect(@org.jetbrains.annotations.NotNull()
    com.example.engine.model.Player p) {
    }
    
    public final void addPair() {
    }
    
    public final void setBye(@org.jetbrains.annotations.NotNull()
    com.example.engine.model.Player p) {
    }
    
    public final void clearLastPair() {
    }
    
    public final boolean canSave() {
        return false;
    }
    
    public final void save(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSaved) {
    }
}