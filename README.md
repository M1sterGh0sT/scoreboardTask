# Description of the scoreboardTask

## What I built

Four operations:
- start a match (initial score is always 0-0)
- update score (scores are absolute, not incremental)
- finish a match (removes it from the board)
- get a summary (sorted by total score, ties broken by most recently started)


## Main Files

- `Match.java` - holds all the data for a single match (team names, scores, start time).
- `ScoreboardService.java` - interface that defines the four operations. Keeps the contract separate from the implementation.
- `Scoreboard.java` - the actual logic. Implements `ScoreboardService` and manages the list of matches.
- `ScoreboardTest.java` - unit tests covering all four operations and edge cases.


## Edge cases

- null or blank team names
- same team on both sides
- duplicate matches
- negative scores
- score going lower than current value
- updating or finishing a match that doesn't exist

## Assumptions

- Scores can only increase. In real football a score never goes down, so I throw an exception if someone tries to lower it.
- A team can only play one match at a time.
- Score updates are absolute - calling `updateScore("Mexico", "Canada", 2, 1)` means the score is 2-1, not that 2 goals were just scored.

