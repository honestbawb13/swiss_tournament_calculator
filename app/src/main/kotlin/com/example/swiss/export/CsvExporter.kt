package com.example.swiss.export

import com.example.engine.SwissEngine
import com.example.engine.model.TournamentState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class CsvExporter(private val engine: SwissEngine = SwissEngine()) {
    private fun nowIso(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        return sdf.format(Date())
    }
    fun standingsCsv(state: TournamentState, tournamentName: String, unlockedRounds: Set<Int> = emptySet()): String {
        val sb = StringBuilder()
        sb.appendLine("meta,tournament_name,$tournamentName")
        sb.appendLine("meta,locked,${state.locked}")
        sb.appendLine("meta,generation_time,${nowIso()}")
        if (unlockedRounds.isNotEmpty()) sb.appendLine("meta,unlocked_rounds,${unlockedRounds.sorted().joinToString("|") { (it + 1).toString() }}")
        sb.appendLine("player_id,player_name,match_points,omw_pct,game_win_pct,sonneborn_berger")
        engine.standings(state).forEach { s ->
            sb.appendLine(
                listOf(
                    s.player.id.value,
                    s.player.name,
                    s.matchPoints,
                    String.format("%.4f", s.opponentsMatchWinPct),
                    String.format("%.4f", s.gameWinPct),
                    s.sonnebornBerger
                ).joinToString(",")
            )
        }
        return sb.toString()
    }

    fun matchesCsv(state: TournamentState, tournamentName: String, unlockedRounds: Set<Int> = emptySet()): String {
        val sb = StringBuilder()
        sb.appendLine("meta,tournament_name,$tournamentName")
        sb.appendLine("meta,locked,${state.locked}")
        sb.appendLine("meta,generation_time,${nowIso()}")
        if (unlockedRounds.isNotEmpty()) sb.appendLine("meta,unlocked_rounds,${unlockedRounds.sorted().joinToString("|") { (it + 1).toString() }}")
        sb.appendLine("round,match_id,home_player_id,away_player_id,home_games_won,away_games_won,draws")
        state.rounds.sortedBy { it.index }.forEach { r ->
            r.matches.forEach { m ->
                val res = m.result
                sb.appendLine(
                    listOf(
                        r.index + 1,
                        m.id.value,
                        m.home?.value ?: "",
                        m.away?.value ?: "",
                        res?.homeGamesWon ?: "",
                        res?.awayGamesWon ?: "",
                        res?.draws ?: ""
                    ).joinToString(",")
                )
            }
        }
        return sb.toString()
    }
}
