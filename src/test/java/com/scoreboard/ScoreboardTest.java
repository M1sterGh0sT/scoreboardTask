package com.scoreboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ScoreboardTest {

    private ScoreboardService scoreboard;

    @BeforeEach
    void setUp() {
        scoreboard = new Scoreboard();
    }

    @Test
    void shouldStartMatchWithZeroScore() {
        scoreboard.startMatch("Mexico", "Canada");
        List<Match> summary = scoreboard.getSummary();

        assertEquals(1, summary.size());
        assertEquals(0, summary.get(0).getHomeScore());
        assertEquals(0, summary.get(0).getAwayScore());
    }

    @Test
    void shouldThrowWhenStartingDuplicateMatch() {
        scoreboard.startMatch("Mexico", "Canada");

        assertThrows(IllegalArgumentException.class, () ->
                scoreboard.startMatch("Mexico", "Canada"));
    }

    @Test
    void shouldThrowWhenTeamNameIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                scoreboard.startMatch(null, "Canada"));

        assertThrows(IllegalArgumentException.class, () ->
                scoreboard.startMatch("Mexico", null));
    }

    @Test
    void shouldThrowWhenTeamNameIsBlank() {
        assertThrows(IllegalArgumentException.class, () ->
                scoreboard.startMatch("", "Canada"));

        assertThrows(IllegalArgumentException.class, () ->
                scoreboard.startMatch("Mexico", ""));
    }

    @Test
    void shouldThrowWhenTeamsAreTheSame() {
        assertThrows(IllegalArgumentException.class, () ->
                scoreboard.startMatch("Mexico", "Mexico"));
    }

    @Test
    void shouldUpdateScore() {
        scoreboard.startMatch("Mexico", "Canada");
        scoreboard.updateScore("Mexico", "Canada", 1, 2);

        Match match = scoreboard.getSummary().get(0);
        assertEquals(1, match.getHomeScore());
        assertEquals(2, match.getAwayScore());
    }

    @Test
    void shouldThrowWhenUpdatingNonExistentMatch() {
        assertThrows(IllegalArgumentException.class, () ->
                scoreboard.updateScore("Mexico", "Canada", 1, 0));
    }

    @Test
    void shouldThrowWhenScoreIsNegative() {
        scoreboard.startMatch("Mexico", "Canada");

        assertThrows(IllegalArgumentException.class, () ->
                scoreboard.updateScore("Mexico", "Canada", -1, 0));
    }

    @Test
    void shouldThrowWhenScoreIsLowerThanCurrent() {
        scoreboard.startMatch("Mexico", "Canada");
        scoreboard.updateScore("Mexico", "Canada", 2, 1);

        assertThrows(IllegalArgumentException.class, () ->
                scoreboard.updateScore("Mexico", "Canada", 1, 1));
    }


    @Test
    void shouldFinishMatch() {
        scoreboard.startMatch("Mexico", "Canada");
        scoreboard.finishMatch("Mexico", "Canada");

        assertEquals(0, scoreboard.getSummary().size());
    }

    @Test
    void shouldThrowWhenFinishingNonExistentMatch() {
        assertThrows(IllegalArgumentException.class, () ->
                scoreboard.finishMatch("Mexico", "Canada"));
    }


    @Test
    void shouldReturnMatchesOrderedByTotalScore() {
        scoreboard.startMatch("Mexico", "Canada");
        scoreboard.updateScore("Mexico", "Canada", 0, 5);

        scoreboard.startMatch("Spain", "Brazil");
        scoreboard.updateScore("Spain", "Brazil", 10, 2);

        List<Match> summary = scoreboard.getSummary();

        assertEquals("Spain", summary.get(0).getHomeTeam());
        assertEquals("Mexico", summary.get(1).getHomeTeam());
    }

    @Test
    void shouldReturnMostRecentMatchFirstWhenTotalScoreIsEqual() {
        scoreboard.startMatch("Mexico", "Canada");
        scoreboard.updateScore("Mexico", "Canada", 0, 5);

        scoreboard.startMatch("Uruguay", "Italy");
        scoreboard.updateScore("Uruguay", "Italy", 6, 6);

        scoreboard.startMatch("Spain", "Brazil");
        scoreboard.updateScore("Spain", "Brazil", 10, 2);

        List<Match> summary = scoreboard.getSummary();

        assertEquals("Spain", summary.get(0).getHomeTeam());
        assertEquals("Uruguay", summary.get(1).getHomeTeam());
    }

    @Test
    void shouldReturnCorrectSummaryFromTaskExample() {
        scoreboard.startMatch("Mexico", "Canada");
        scoreboard.updateScore("Mexico", "Canada", 0, 5);

        scoreboard.startMatch("Spain", "Brazil");
        scoreboard.updateScore("Spain", "Brazil", 10, 2);

        scoreboard.startMatch("Germany", "France");
        scoreboard.updateScore("Germany", "France", 2, 2);

        scoreboard.startMatch("Uruguay", "Italy");
        scoreboard.updateScore("Uruguay", "Italy", 6, 6);

        scoreboard.startMatch("Argentina", "Australia");
        scoreboard.updateScore("Argentina", "Australia", 3, 1);

        List<Match> summary = scoreboard.getSummary();

        assertEquals("Uruguay", summary.get(0).getHomeTeam());
        assertEquals("Spain", summary.get(1).getHomeTeam());
        assertEquals("Mexico", summary.get(2).getHomeTeam());
        assertEquals("Argentina", summary.get(3).getHomeTeam());
        assertEquals("Germany", summary.get(4).getHomeTeam());
    }

    @Test
    void shouldReturnEmptySummaryWhenNoMatches() {
        assertEquals(0, scoreboard.getSummary().size());
    }
}
