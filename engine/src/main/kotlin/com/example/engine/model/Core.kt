package com.example.engine.model

import kotlin.math.ceil

data class PlayerId(val value: String)

data class Player(
    val id: PlayerId,
    val name: String,
)

enum class BestOf { BO1, BO3 }

enum class GameOutcome { WIN, LOSS, DRAW }

data class GameResult(
    val home: GameOutcome,
)

data class MatchId(val value: String)

data class MatchResult(
    val homeGamesWon: Int,
    val awayGamesWon: Int,
    val draws: Int,
) {
    fun isComplete(bestOf: BestOf): Boolean {
        return when (bestOf) {
            BestOf.BO1 -> (homeGamesWon + awayGamesWon + draws) == 1
            BestOf.BO3 -> (homeGamesWon + awayGamesWon + draws) in 2..3
        }
    }

    fun matchPointsForHome(): Int = when {
        homeGamesWon > awayGamesWon -> 3
        homeGamesWon < awayGamesWon -> 0
        else -> 1
    }

    fun matchPointsForAway(): Int = 3 - matchPointsForHome()
}

data class Match(
    val id: MatchId,
    val roundIndex: Int, // 0-based
    val home: PlayerId?, // null indicates BYE side
    val away: PlayerId?,
    val result: MatchResult? = null,
)

data class Round(
    val index: Int,
    val matches: List<Match>,
)

data class TournamentConfig(
    val bestOf: BestOf,
    val allowDraws: Boolean,
    val roundsPlanned: Int,
    val randomFirstRound: Boolean,
)

data class TournamentState(
    val players: List<Player>,
    val config: TournamentConfig,
    val rounds: List<Round>,
    val locked: Boolean = true, // must be explicitly unlocked for edits
)

data class Standing(
    val player: Player,
    val matchPoints: Int,
    val opponentsMatchWinPct: Double,
    val gameWinPct: Double,
    val sonnebornBerger: Int,
)

object RoundSuggester {
    fun suggestedRounds(players: Int): Int = ceil(log2Ceil(players)).toInt()

    private fun log2Ceil(n: Int): Double {
        var x = 1
        var p = 0
        while (x < n) {
            x = x shl 1
            p++
        }
        return p.toDouble()
    }
}

