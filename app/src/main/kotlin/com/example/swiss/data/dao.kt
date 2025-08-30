package com.example.swiss.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface TournamentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: TournamentEntity)

    @Query("SELECT * FROM tournaments ORDER BY createdAt DESC")
    suspend fun list(): List<TournamentEntity>

    @Query("SELECT * FROM tournaments WHERE id = :id LIMIT 1")
    suspend fun get(id: String): TournamentEntity?

    @Query("DELETE FROM tournaments WHERE id = :id")
    suspend fun delete(id: String)
}

@Dao
interface PlayerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(players: List<PlayerEntity>)

    @Query("SELECT * FROM players WHERE tournamentId = :tid ORDER BY seed ASC")
    suspend fun listByTournament(tid: String): List<PlayerEntity>

    @Query("DELETE FROM players WHERE tournamentId = :tid")
    suspend fun deleteByTournament(tid: String)
}

@Dao
interface MatchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(matches: List<MatchEntity>)

    @Query("SELECT * FROM matches WHERE tournamentId = :tid ORDER BY roundIndex ASC")
    suspend fun listByTournament(tid: String): List<MatchEntity>

    @Query("DELETE FROM matches WHERE tournamentId = :tid")
    suspend fun deleteByTournament(tid: String)

    @Query("DELETE FROM matches WHERE tournamentId = :tid AND roundIndex = :roundIndex")
    suspend fun deleteByRound(tid: String, roundIndex: Int)
}

@Dao
interface UnlockDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: UnlockEventEntity)

    @Query("SELECT roundIndex FROM unlock_events WHERE tournamentId = :tid")
    suspend fun listUnlockedRounds(tid: String): List<Int>
}
