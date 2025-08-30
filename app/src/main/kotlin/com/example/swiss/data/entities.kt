package com.example.swiss.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "tournaments")
data class TournamentEntity(
    @PrimaryKey val id: String,
    val name: String,
    val bestOf: String,
    val allowDraws: Boolean,
    val roundsPlanned: Int,
    val randomFirstRound: Boolean,
    val locked: Boolean,
    val createdAt: Long,
)

@Entity(
    tableName = "players",
    indices = [Index(value = ["tournamentId"])])
data class PlayerEntity(
    @PrimaryKey val id: String,
    val tournamentId: String,
    val name: String,
    val seed: Int,
)

@Entity(
    tableName = "matches",
    indices = [Index(value = ["tournamentId", "roundIndex"])])
data class MatchEntity(
    @PrimaryKey val id: String,
    val tournamentId: String,
    val roundIndex: Int,
    val homePlayerId: String?,
    val awayPlayerId: String?,
    val homeGamesWon: Int?,
    val awayGamesWon: Int?,
    val draws: Int?,
)

