package com.example.swiss.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\nH&\u00a8\u0006\u000b"}, d2 = {"Lcom/example/swiss/data/SwissDatabase;", "Landroidx/room/RoomDatabase;", "()V", "matches", "Lcom/example/swiss/data/MatchDao;", "players", "Lcom/example/swiss/data/PlayerDao;", "tournaments", "Lcom/example/swiss/data/TournamentDao;", "unlocks", "Lcom/example/swiss/data/UnlockDao;", "app_debug"})
@androidx.room.Database(entities = {com.example.swiss.data.TournamentEntity.class, com.example.swiss.data.PlayerEntity.class, com.example.swiss.data.MatchEntity.class, com.example.swiss.data.UnlockEventEntity.class}, version = 2, exportSchema = true)
public abstract class SwissDatabase extends androidx.room.RoomDatabase {
    
    public SwissDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.swiss.data.TournamentDao tournaments();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.swiss.data.PlayerDao players();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.swiss.data.MatchDao matches();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.swiss.data.UnlockDao unlocks();
}