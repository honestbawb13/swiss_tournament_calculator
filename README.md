Swiss Tournament Calculator (Android)

Overview
- Native Kotlin + Jetpack Compose app with a pure-Kotlin engine.
- Supports 4–64 players, random pairings, draws allowed, 3-1-0 scoring.
- Best of 1 or Best of 3; tracks game wins for tiebreakers.
- First round random by default; manual entry supported.
- Local persistence via Room; CSV export for standings and matches.
- Partial resume supported with explicit unlock to edit past data.

Modules
- `:engine`: Pure Kotlin Swiss engine (pairings, scoring, tiebreakers, CSV fields).
- `:app`: Android UI, Room, Hilt, Navigation, export stubs.

Pairings
- Random within score groups; avoids rematches when possible; assigns a single bye per round when odd players (counts as a win for points; excluded from opponent tiebreakers).
- First round: random if enabled; otherwise use current standings grouping.

Scoring
- Match points: Win=3, Draw=1, Loss=0. Bye=3.
- Game wins tracked for Game Win % (for Bo3 and Bo1 draws).

Tiebreakers (ordering)
1) Match Points
2) Opponents’ Match Win % (OMW%)
3) Game Win %
4) Sonneborn–Berger
5) Head-to-head (planned in UI when exactly two players tied)

CSV Export
- `standings.csv`: `player_id,player_name,match_points,omw_pct,game_win_pct,sonneborn_berger`
- `matches.csv`: `round,match_id,home_player_id,away_player_id,home_games_won,away_games_won,draws`
- Metadata rows added first as plain rows: `meta,tournament_name,<name>`, `meta,locked,<true|false>`, `meta,generation_time,<iso>`

Unlock and Partial Resume
- Tournaments are locked by default. Unlock to edit any past round and re-pair subsequent rounds.
- UI displays an obvious banner and requires explicit confirmation when unlocking.
- CSV exports include `locked` status in metadata rows.

Build Notes
- Min SDK 24, target/compile 34, Java 17.
- The repository contains Gradle Kotlin DSL files. You’ll need a local Gradle and Android SDK to build.

Next Steps
- Wire Room repositories into ViewModels and UI.
- Add screens for player entry, round management, manual pairing overrides, and export via Storage Access Framework.
- Add import for CSV to recreate or clone tournaments.

