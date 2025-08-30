package com.example.engine

import com.example.engine.model.*
import kotlin.random.Random

class SwissEngine(private val rng: Random = Random.Default) {

    fun standings(state: TournamentState): List<Standing> {
        val playersById = state.players.associateBy { it.id }
        val matches = state.rounds.flatMap { it.matches }

        val mp = mutableMapOf<PlayerId, Int>().withDefault { 0 }
        val gw = mutableMapOf<PlayerId, Int>().withDefault { 0 }
        val gl = mutableMapOf<PlayerId, Int>().withDefault { 0 }
        val gd = mutableMapOf<PlayerId, Int>().withDefault { 0 }
        val opps = mutableMapOf<PlayerId, MutableList<PlayerId>>()

        fun addOpp(a: PlayerId, b: PlayerId) {
            opps.getOrPut(a) { mutableListOf() }.add(b)
        }

        matches.forEach { m ->
            val r = m.result ?: return@forEach
            val home = m.home
            val away = m.away
            if (home == null && away != null) {
                // BYE for away? We model BYE as the null opponent; award 3 points to non-null
                mp[away] = mp.getValue(away) + 3
                return@forEach
            }
            if (away == null && home != null) {
                mp[home] = mp.getValue(home) + 3
                return@forEach
            }
            if (home == null || away == null) return@forEach

            mp[home] = mp.getValue(home) + r.matchPointsForHome()
            mp[away] = mp.getValue(away) + r.matchPointsForAway()

            gw[home] = gw.getValue(home) + r.homeGamesWon
            gl[home] = gl.getValue(home) + r.awayGamesWon
            gd[home] = gd.getValue(home) + r.draws

            gw[away] = gw.getValue(away) + r.awayGamesWon
            gl[away] = gl.getValue(away) + r.homeGamesWon
            gd[away] = gd.getValue(away) + r.draws

            addOpp(home, away)
            addOpp(away, home)
        }

        val omwPct = state.players.associate { p ->
            val oppList = opps[p.id].orEmpty()
            if (oppList.isEmpty()) p.id to 0.0
            else {
                val oppWinPcts = oppList.map { oid ->
                    val oppPoints = mp.getValue(oid)
                    val oppMatches = matches.count { (it.home == oid || it.away == oid) && it.result != null && (it.home != null && it.away != null) }
                    val maxPoints = oppMatches * 3.0
                    if (maxPoints <= 0.0) 0.0 else (oppPoints / maxPoints)
                }
                p.id to oppWinPcts.average()
            }
        }

        val gameWinPct = state.players.associate { p ->
            val wins = gw.getValue(p.id).toDouble()
            val losses = gl.getValue(p.id).toDouble()
            val draws = gd.getValue(p.id).toDouble()
            val total = wins + losses + draws
            p.id to if (total <= 0.0) 0.0 else (wins + 0.5 * draws) / total
        }

        val sb = state.players.associate { p ->
            val playerId = p.id
            // Sonneborn–Berger: sum of defeated opponents' match points + half of drawn opponents' match points
            var score = 0.0
            matches.forEach { m ->
                val r = m.result ?: return@forEach
                val home = m.home
                val away = m.away
                if (home == null || away == null) return@forEach
                if (home == playerId) {
                    val oppPts = mp.getValue(away)
                    score += when {
                        r.homeGamesWon > r.awayGamesWon -> oppPts.toDouble()
                        r.homeGamesWon == r.awayGamesWon -> oppPts / 2.0
                        else -> 0.0
                    }
                } else if (away == playerId) {
                    val oppPts = mp.getValue(home)
                    score += when {
                        r.awayGamesWon > r.homeGamesWon -> oppPts.toDouble()
                        r.awayGamesWon == r.homeGamesWon -> oppPts / 2.0
                        else -> 0.0
                    }
                }
            }
            p.id to score.toInt()
        }

        val standings = state.players.map { p ->
            Standing(
                player = p,
                matchPoints = mp.getValue(p.id),
                opponentsMatchWinPct = omwPct.getValue(p.id),
                gameWinPct = gameWinPct.getValue(p.id),
                sonnebornBerger = sb.getValue(p.id),
            )
        }

        fun headToHeadCompare(a: PlayerId, b: PlayerId): Int {
            // If exactly one match exists between a and b, use that result to break ties
            val h2h = matches.firstOrNull { m ->
                val home = m.home; val away = m.away
                home != null && away != null && ((home == a && away == b) || (home == b && away == a)) && m.result != null
            } ?: return 0
            val r = h2h.result!!
            return when {
                r.homeGamesWon > r.awayGamesWon && h2h.home == a -> -1
                r.homeGamesWon > r.awayGamesWon && h2h.home == b -> 1
                r.awayGamesWon > r.homeGamesWon && h2h.away == a -> -1
                r.awayGamesWon > r.homeGamesWon && h2h.away == b -> 1
                else -> 0
            }
        }

        return standings.sortedWith(
            compareByDescending<Standing> { it.matchPoints }
                .thenByDescending { it.opponentsMatchWinPct }
                .thenByDescending { it.gameWinPct }
                .thenByDescending { it.sonnebornBerger }
                .thenComparator { a, b -> headToHeadCompare(a.player.id, b.player.id) }
        )
    }

    fun generateNextRound(state: TournamentState): Round {
        val completedRounds = state.rounds.size
        val resultsUpToNow = standings(state)
        val players = state.players
        val byId = players.associateBy { it.id }

        val priorPairings = state.rounds.flatMap { it.matches }
            .mapNotNull { m ->
                val a = m.home
                val b = m.away
                if (a != null && b != null) setOf(a, b) else null
            }
            .toSet()

        val odd = players.size % 2 == 1
        val alreadyHadBye = mutableSetOf<PlayerId>()
        state.rounds.flatMap { it.matches }.forEach { m ->
            val r = m.result
            if (m.home == null && m.away != null) alreadyHadBye.add(m.away)
            if (m.away == null && m.home != null) alreadyHadBye.add(m.home)
        }

        val pool = if (completedRounds == 0 && state.config.randomFirstRound) {
            players.shuffled(rng)
        } else {
            // Random within current score ordering
            val groups = resultsUpToNow.groupBy { it.matchPoints }
                .toSortedMap(compareByDescending { it })
            groups.values.flatMap { g -> g.shuffled(rng).map { it.player } }
        }.toMutableList()

        var byePlayer: PlayerId? = null
        if (odd) {
            val candidates = pool.map { it.id }.filter { it !in alreadyHadBye }
            byePlayer = (if (candidates.isNotEmpty()) candidates else pool.map { it.id }).random(rng)
            pool.removeIf { it.id == byePlayer }
        }

        val matches = mutableListOf<Match>()
        val used = mutableSetOf<PlayerId>()

        fun hasPlayed(a: PlayerId, b: PlayerId): Boolean = setOf(a, b) in priorPairings

        val ids = pool.map { it.id }.toMutableList()
        while (ids.size >= 2) {
            val a = ids.removeAt(0)
            var idx = ids.indexOfFirst { b -> !hasPlayed(a, b) }
            if (idx == -1) idx = 0 // allow rematch only if necessary
            val b = ids.removeAt(idx)
            used.add(a)
            used.add(b)
            matches.add(
                Match(
                    id = MatchId("r${completedRounds + 1}_${matches.size}"),
                    roundIndex = completedRounds,
                    home = a,
                    away = b,
                    result = null
                )
            )
        }

        if (byePlayer != null) {
            matches.add(
                Match(
                    id = MatchId("r${completedRounds + 1}_bye"),
                    roundIndex = completedRounds,
                    home = byePlayer,
                    away = null,
                    result = MatchResult(homeGamesWon = 1, awayGamesWon = 0, draws = 0)
                )
            )
        }

        return Round(index = completedRounds, matches = matches)
    }
}
