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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0018\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001Bm\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u001a\b\u0002\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\n0\u0007\u0012\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u000eJ\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0003J\u001b\u0010\u001d\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\n0\u0007H\u00c6\u0003J\u000f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0003J\u000b\u0010\u001f\u001a\u0004\u0018\u00010\bH\u00c6\u0003J\u000b\u0010 \u001a\u0004\u0018\u00010\u0003H\u00c6\u0003Js\u0010!\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u001a\b\u0002\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\n0\u00072\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010%\u001a\u00020&H\u00d6\u0001J\t\u0010\'\u001a\u00020\u0003H\u00d6\u0001R\u0013\u0010\f\u001a\u0004\u0018\u00010\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\r\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R#\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\n0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0012R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0014\u00a8\u0006("}, d2 = {"Lcom/example/swiss/ui/ManualPairingState;", "", "tid", "", "state", "Lcom/example/engine/model/TournamentState;", "unpaired", "", "Lcom/example/engine/model/Player;", "pairs", "Lkotlin/Pair;", "selected", "bye", "error", "(Ljava/lang/String;Lcom/example/engine/model/TournamentState;Ljava/util/List;Ljava/util/List;Ljava/util/List;Lcom/example/engine/model/Player;Ljava/lang/String;)V", "getBye", "()Lcom/example/engine/model/Player;", "getError", "()Ljava/lang/String;", "getPairs", "()Ljava/util/List;", "getSelected", "getState", "()Lcom/example/engine/model/TournamentState;", "getTid", "getUnpaired", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class ManualPairingState {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String tid = null;
    @org.jetbrains.annotations.Nullable()
    private final com.example.engine.model.TournamentState state = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.example.engine.model.Player> unpaired = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<kotlin.Pair<com.example.engine.model.Player, com.example.engine.model.Player>> pairs = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.example.engine.model.Player> selected = null;
    @org.jetbrains.annotations.Nullable()
    private final com.example.engine.model.Player bye = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String error = null;
    
    public ManualPairingState(@org.jetbrains.annotations.NotNull()
    java.lang.String tid, @org.jetbrains.annotations.Nullable()
    com.example.engine.model.TournamentState state, @org.jetbrains.annotations.NotNull()
    java.util.List<com.example.engine.model.Player> unpaired, @org.jetbrains.annotations.NotNull()
    java.util.List<kotlin.Pair<com.example.engine.model.Player, com.example.engine.model.Player>> pairs, @org.jetbrains.annotations.NotNull()
    java.util.List<com.example.engine.model.Player> selected, @org.jetbrains.annotations.Nullable()
    com.example.engine.model.Player bye, @org.jetbrains.annotations.Nullable()
    java.lang.String error) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTid() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.example.engine.model.TournamentState getState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.example.engine.model.Player> getUnpaired() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<kotlin.Pair<com.example.engine.model.Player, com.example.engine.model.Player>> getPairs() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.example.engine.model.Player> getSelected() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.example.engine.model.Player getBye() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getError() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.example.engine.model.TournamentState component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.example.engine.model.Player> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<kotlin.Pair<com.example.engine.model.Player, com.example.engine.model.Player>> component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.example.engine.model.Player> component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.example.engine.model.Player component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.swiss.ui.ManualPairingState copy(@org.jetbrains.annotations.NotNull()
    java.lang.String tid, @org.jetbrains.annotations.Nullable()
    com.example.engine.model.TournamentState state, @org.jetbrains.annotations.NotNull()
    java.util.List<com.example.engine.model.Player> unpaired, @org.jetbrains.annotations.NotNull()
    java.util.List<kotlin.Pair<com.example.engine.model.Player, com.example.engine.model.Player>> pairs, @org.jetbrains.annotations.NotNull()
    java.util.List<com.example.engine.model.Player> selected, @org.jetbrains.annotations.Nullable()
    com.example.engine.model.Player bye, @org.jetbrains.annotations.Nullable()
    java.lang.String error) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}