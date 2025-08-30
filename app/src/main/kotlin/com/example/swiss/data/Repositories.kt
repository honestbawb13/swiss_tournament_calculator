package com.example.swiss.data

import com.example.engine.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class TournamentRepository(
    private val db: SwissDatabase,
) {
    suspend fun listTournaments(): List<TournamentEntity> = withContext(Dispatchers.IO) {
        db.tournaments().list()
    }
    suspend fun createTournament(
        name: String,
        playerNames: List<String>,
        bestOf: BestOf,
        allowDraws: Boolean,
        roundsPlanned: Int,
        randomFirstRound: Boolean,
        locked: Boolean = true,
    ): String = withContext(Dispatchers.IO) {
        val tid = UUID.randomUUID().toString()
        db.tournaments().upsert(
            TournamentEntity(
                id = tid,
                name = name,
                bestOf = bestOf.name,
                allowDraws = allowDraws,
                roundsPlanned = roundsPlanned,
                randomFirstRound = randomFirstRound,
                locked = locked,
                createdAt = System.currentTimeMillis(),
            )
        )
        val players = playerNames.mapIndexed { index, s ->
            PlayerEntity(
                id = UUID.randomUUID().toString(),
                tournamentId = tid,
                name = s.trim(),
                seed = index
            )
        }
        db.players().upsertAll(players)
        tid
    }

    suspend fun loadTournamentState(tid: String): TournamentState? = withContext(Dispatchers.IO) {
        val t = db.tournaments().get(tid) ?: return@withContext null
        val players = db.players().listByTournament(tid)
        val matches = db.matches().listByTournament(tid)
        val rounds = matches.groupBy { it.roundIndex }.toSortedMap().map { (idx, group) ->
            Round(
                index = idx,
                matches = group.map { m ->
                    Match(
                        id = MatchId(m.id),
                        roundIndex = m.roundIndex,
                        home = m.homePlayerId?.let { PlayerId(it) },
                        away = m.awayPlayerId?.let { PlayerId(it) },
                        result = if (m.homeGamesWon != null && m.awayGamesWon != null && m.draws != null) {
                            MatchResult(m.homeGamesWon, m.awayGamesWon, m.draws)
                        } else null
                    )
                }
            )
        }

        TournamentState(
            players = players.map { Player(PlayerId(it.id), it.name) },
            config = TournamentConfig(
                bestOf = BestOf.valueOf(t.bestOf),
                allowDraws = t.allowDraws,
                roundsPlanned = t.roundsPlanned,
                randomFirstRound = t.randomFirstRound,
            ),
            rounds = rounds,
            locked = t.locked,
        )
    }

    suspend fun saveRound(tid: String, round: Round) = withContext(Dispatchers.IO) {
        val ms = round.matches.map { m ->
            MatchEntity(
                id = m.id.value,
                tournamentId = tid,
                roundIndex = round.index,
                homePlayerId = m.home?.value,
                awayPlayerId = m.away?.value,
                homeGamesWon = m.result?.homeGamesWon,
                awayGamesWon = m.result?.awayGamesWon,
                draws = m.result?.draws,
            )
        }
        db.matches().upsertAll(ms)
    }

    suspend fun replaceRound(tid: String, round: Round) = withContext(Dispatchers.IO) {
        db.matches().deleteByRound(tid, round.index)
        saveRound(tid, round)
    }

    suspend fun setLocked(tid: String, locked: Boolean) = withContext(Dispatchers.IO) {
        val t = db.tournaments().get(tid) ?: return@withContext
        db.tournaments().upsert(t.copy(locked = locked))
    }

    suspend fun getTournamentMeta(tid: String): TournamentEntity? = withContext(Dispatchers.IO) {
        db.tournaments().get(tid)
    }

    suspend fun listUnlockedRounds(tid: String): Set<Int> = withContext(Dispatchers.IO) {
        db.unlocks().listUnlockedRounds(tid).toSet()
    }

    suspend fun unlockRound(tid: String, roundIndex: Int) = withContext(Dispatchers.IO) {
        db.unlocks().insert(UnlockEventEntity(tournamentId = tid, roundIndex = roundIndex, timestamp = System.currentTimeMillis()))
    }
}
