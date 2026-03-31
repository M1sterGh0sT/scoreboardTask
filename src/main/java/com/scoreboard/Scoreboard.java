package com.scoreboard;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard implements ScoreboardService {
    private final List<Match> matches = new ArrayList<>();

    @Override
    public void startMatch(String homeTeam, String awayTeam) {
        validateTeams(homeTeam, awayTeam);

        boolean match = matches.stream()
                .anyMatch(m -> m.getHomeTeam().equals(homeTeam)
                        && m.getAwayTeam().equals(awayTeam));
        if(match) {
            throw new IllegalArgumentException("This match already created");
        }

        matches.add(new Match(homeTeam, awayTeam));
    }

    @Override
    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        validateTeams(homeTeam, awayTeam);

        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Score cannot be negative");
        }

        Match match = findMatch(homeTeam, awayTeam);

        if (homeScore < match.getHomeScore() || awayScore < match.getAwayScore()) {
            throw new IllegalArgumentException("Score cannot be lower than it was");
        }

        match.updateScore(homeScore, awayScore);
    }

    @Override
    public void finishMatch(String homeTeam, String awayTeam) {
        // TODO
    }

    @Override
    public List<Match> getSummary() {
        // TODO
        return new ArrayList<>();
    }


    private Match findMatch(String homeTeam, String awayTeam) {
        return matches.stream()
                .filter(m -> m.getHomeTeam().equals(homeTeam)
                        && m.getAwayTeam().equals(awayTeam))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Match not found"));
    }

    private void validateTeams(String homeTeam, String awayTeam) {
        if(homeTeam == null || awayTeam == null) {
            throw new IllegalArgumentException("Teams cannot be null");
        }
        if(homeTeam.isBlank() || awayTeam.isBlank()) {
            throw new IllegalArgumentException("Teams cannot be blank");
        }
    }

}

