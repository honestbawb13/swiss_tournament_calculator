package com.example.swiss.data;

import com.example.engine.model.*;
import kotlinx.coroutines.Dispatchers;
import java.util.UUID;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004JN\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\r2\b\b\u0002\u0010\u0011\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010\u0012J\u0018\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u0006H\u0086@\u00a2\u0006\u0002\u0010\u0016J\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00140\tH\u0086@\u00a2\u0006\u0002\u0010\u0018J\u001c\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u000f0\u001a2\u0006\u0010\u0015\u001a\u00020\u0006H\u0086@\u00a2\u0006\u0002\u0010\u0016J\u0018\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u0015\u001a\u00020\u0006H\u0086@\u00a2\u0006\u0002\u0010\u0016J\u001e\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020 H\u0086@\u00a2\u0006\u0002\u0010!J\u001e\u0010\"\u001a\u00020\u001e2\u0006\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010#J\u001e\u0010$\u001a\u00020\u001e2\u0006\u0010\u0015\u001a\u00020\u00062\u0006\u0010%\u001a\u00020\u000fH\u0086@\u00a2\u0006\u0002\u0010&R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\'"}, d2 = {"Lcom/example/swiss/data/TournamentRepository;", "", "db", "Lcom/example/swiss/data/SwissDatabase;", "(Lcom/example/swiss/data/SwissDatabase;)V", "createTournament", "", "name", "playerNames", "", "bestOf", "Lcom/example/engine/model/BestOf;", "allowDraws", "", "roundsPlanned", "", "randomFirstRound", "locked", "(Ljava/lang/String;Ljava/util/List;Lcom/example/engine/model/BestOf;ZIZZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getTournamentMeta", "Lcom/example/swiss/data/TournamentEntity;", "tid", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "listTournaments", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "listUnlockedRounds", "", "loadTournamentState", "Lcom/example/engine/model/TournamentState;", "saveRound", "", "round", "Lcom/example/engine/model/Round;", "(Ljava/lang/String;Lcom/example/engine/model/Round;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setLocked", "(Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "unlockRound", "roundIndex", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class TournamentRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.example.swiss.data.SwissDatabase db = null;
    
    public TournamentRepository(@org.jetbrains.annotations.NotNull()
    com.example.swiss.data.SwissDatabase db) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object listTournaments(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.swiss.data.TournamentEntity>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object createTournament(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> playerNames, @org.jetbrains.annotations.NotNull()
    com.example.engine.model.BestOf bestOf, boolean allowDraws, int roundsPlanned, boolean randomFirstRound, boolean locked, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object loadTournamentState(@org.jetbrains.annotations.NotNull()
    java.lang.String tid, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.engine.model.TournamentState> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object saveRound(@org.jetbrains.annotations.NotNull()
    java.lang.String tid, @org.jetbrains.annotations.NotNull()
    com.example.engine.model.Round round, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setLocked(@org.jetbrains.annotations.NotNull()
    java.lang.String tid, boolean locked, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getTournamentMeta(@org.jetbrains.annotations.NotNull()
    java.lang.String tid, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.swiss.data.TournamentEntity> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object listUnlockedRounds(@org.jetbrains.annotations.NotNull()
    java.lang.String tid, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.Set<java.lang.Integer>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object unlockRound(@org.jetbrains.annotations.NotNull()
    java.lang.String tid, int roundIndex, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}