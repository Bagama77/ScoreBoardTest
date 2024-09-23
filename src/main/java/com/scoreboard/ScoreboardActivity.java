package com.scoreboard;

public interface ScoreboardActivity {
    BetMatch startAMatch(String homeTeam, String guestTeam);
    boolean updateScore(String homeTeam, int homeTeamScore, int guestTeamScore);
    boolean finishMatch(String homeTeam);
    String getSummary();
}


