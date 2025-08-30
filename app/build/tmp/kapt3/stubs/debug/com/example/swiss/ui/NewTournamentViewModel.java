package com.example.swiss.ui;

import androidx.lifecycle.ViewModel;
import com.example.engine.model.BestOf;
import com.example.engine.model.RoundSuggester;
import com.example.swiss.data.TournamentRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\f\u001a\u00020\rJ\u001a\u0010\u000e\u001a\u00020\u000f2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u000f0\u0011J\u000e\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\u0014H\u0002J\b\u0010\u0015\u001a\u00020\u000fH\u0002J\u000e\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u0018J\u000e\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u0012J\u000e\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u0012J\u000e\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\rJ\u000e\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u001dR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u001e"}, d2 = {"Lcom/example/swiss/ui/NewTournamentViewModel;", "Landroidx/lifecycle/ViewModel;", "repo", "Lcom/example/swiss/data/TournamentRepository;", "(Lcom/example/swiss/data/TournamentRepository;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/example/swiss/ui/NewTournamentState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "canCreate", "", "createTournamentAndSeed", "", "onCreated", "Lkotlin/Function1;", "", "playersList", "", "recomputeRounds", "updateBestOf", "v", "Lcom/example/engine/model/BestOf;", "updateName", "updatePlayers", "updateRandomFirst", "updateRounds", "", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class NewTournamentViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.swiss.data.TournamentRepository repo = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.swiss.ui.NewTournamentState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.swiss.ui.NewTournamentState> state = null;
    
    @javax.inject.Inject()
    public NewTournamentViewModel(@org.jetbrains.annotations.NotNull()
    com.example.swiss.data.TournamentRepository repo) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.swiss.ui.NewTournamentState> getState() {
        return null;
    }
    
    public final void updateName(@org.jetbrains.annotations.NotNull()
    java.lang.String v) {
    }
    
    public final void updatePlayers(@org.jetbrains.annotations.NotNull()
    java.lang.String v) {
    }
    
    public final void updateBestOf(@org.jetbrains.annotations.NotNull()
    com.example.engine.model.BestOf v) {
    }
    
    public final void updateRandomFirst(boolean v) {
    }
    
    public final void updateRounds(int v) {
    }
    
    private final void recomputeRounds() {
    }
    
    private final java.util.List<java.lang.String> playersList() {
        return null;
    }
    
    public final boolean canCreate() {
        return false;
    }
    
    public final void createTournamentAndSeed(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onCreated) {
    }
}