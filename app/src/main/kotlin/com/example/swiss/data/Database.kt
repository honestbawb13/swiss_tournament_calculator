package com.example.swiss.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TournamentEntity::class, PlayerEntity::class, MatchEntity::class, UnlockEventEntity::class],
    version = 2,
    exportSchema = true,
)
abstract class SwissDatabase : RoomDatabase() {
    abstract fun tournaments(): TournamentDao
    abstract fun players(): PlayerDao
    abstract fun matches(): MatchDao
    abstract fun unlocks(): UnlockDao
}
