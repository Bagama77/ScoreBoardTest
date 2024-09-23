package com.scoreboard;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ScoreBoardTest {

    private ScoreBoard scoreBoard;

    @Before
    public void setup() {

        this.scoreBoard = new ScoreBoard();
    }

    //Start match
    @Test
    public void startMatchWithTeamsSuccess() {

        BetMatch matchEtalon = new BetMatch("new york", "barcelona");
        BetMatch response = scoreBoard.startAMatch("new york", "barcelona");

        assertEquals(matchEtalon.getHomeTeam(), response.getHomeTeam());
        assertEquals(matchEtalon.getGuestTeam(), response.getGuestTeam());
    }

    @Test
    public void startMatchWithOneTeamNullShouldDenied() {

        BetMatch response = scoreBoard.startAMatch(null, "barcelona");
        assertEquals(response, null);
    }

    @Test
    public void startMatchWithTwoSameTeamsShouldDenied() {

        BetMatch response = scoreBoard.startAMatch("barcelona", "barcelona");
        assertNull(response);
    }

    @Test
    public void startMatchWithTwoTeamsNullShouldDenied() {

        BetMatch response = scoreBoard.startAMatch(null, null);
        assertEquals(response, null);
    }

    @Test
    public void startMatchWithTwoTeamsWithEmptyNamesShouldDenied() {
        BetMatch response = scoreBoard.startAMatch("", "");
        assertNull(response);
    }

    @Test
    public void startMatchWithTeamWhichIsAlreadyInMatchRunningShouldDenied() {

        scoreBoard.startAMatch("new york", "barcelona");
        BetMatch actualResponse = scoreBoard.startAMatch("new york", "burkina-faso");

        assertNull(actualResponse);
    }

    //Update score
    @Test
    public void updateScoreWithValidNewScores_Success() {
        scoreBoard.startAMatch("new york", "barcelona");
        Boolean isMatchUpdated = scoreBoard.updateScore("barcelona", 1, 0);

        int homeScore = scoreBoard.getMatches().get(0).getScoreHomeTeam();
        assertEquals(1, homeScore);
        assertTrue(isMatchUpdated);
    }

    @Test
    public void updateScoreWithTeamNotInBoardShouldFail() {

        scoreBoard.startAMatch("new york", "barcelona");
        Boolean isMatchUpdated = scoreBoard.updateScore("Kiev", 1, 0);

        assertFalse(isMatchUpdated);
    }

    @Test
    public void updateScoreWithHomeTeamValidNewScores_Success() {

        scoreBoard.startAMatch("new york", "barcelona");
        Boolean isMatchUpdated = scoreBoard.updateScore("new york", 1, 0);

        int homeScore = scoreBoard.getMatches().get(0).getScoreHomeTeam();
        assertEquals(1, homeScore);
        assertTrue(isMatchUpdated);

        assertTrue(true);
    }

    @Test
    public void updateScoreWithGuestTeamValidNewScores_Success() {

        scoreBoard.startAMatch("new york", "barcelona");
        Boolean isMatchUpdated = scoreBoard.updateScore("barcelona", 1, 0);

        int homeScore = scoreBoard.getMatches().get(0).getScoreHomeTeam();
        assertEquals(1, homeScore);
        assertTrue(isMatchUpdated);

        isMatchUpdated = scoreBoard.updateScore("barcelona", 1, 1);

        homeScore = scoreBoard.getMatches().get(0).getScoreHomeTeam();
        int guestScore = scoreBoard.getMatches().get(0).getScoreGuestTeam();
        int summaryScore = scoreBoard.getMatches().get(0).getSummaryScore();
        assertEquals(1, homeScore);
        assertEquals(1, guestScore);
        assertEquals(2, summaryScore);
        assertTrue(isMatchUpdated);

    }

    @Test
    public void updateScoreWithInvalidNewScoresShouldFail() {

        scoreBoard.startAMatch("new york", "barcelona");
        Boolean isMatchUpdated = scoreBoard.updateScore("barcelona", -1, 0);

        int homeScore = scoreBoard.getMatches().get(0).getScoreHomeTeam();
        assertEquals(0, homeScore);
        assertFalse(isMatchUpdated);

        isMatchUpdated = scoreBoard.updateScore("barcelona", 2, 0);

        homeScore = scoreBoard.getMatches().get(0).getScoreHomeTeam();
        assertEquals(0, homeScore);
        assertFalse(isMatchUpdated);

        isMatchUpdated = scoreBoard.updateScore("barcelona", 1, -1);

        homeScore = scoreBoard.getMatches().get(0).getScoreHomeTeam();
        int guestScore = scoreBoard.getMatches().get(0).getScoreGuestTeam();

        assertEquals(0, homeScore);
        assertEquals(0, guestScore);
        assertFalse(isMatchUpdated);

        scoreBoard.updateScore("barcelona", 1, 0);
        isMatchUpdated = scoreBoard.updateScore("barcelona", 0, 0);
        homeScore = scoreBoard.getMatches().get(0).getScoreHomeTeam();
        guestScore = scoreBoard.getMatches().get(0).getScoreGuestTeam();

        assertEquals(1, homeScore);
        assertEquals(0, guestScore);
        assertFalse(isMatchUpdated);

    }

    //Finish match

    @Test
    public void finishMatchWithValidTeam_Success() {

        scoreBoard.startAMatch("new york", "barcelona");
        Boolean isMatchUpdated = scoreBoard.updateScore("barcelona", 1, 0);

        boolean isMatchFinished = scoreBoard.finishMatch("new york");
        int matchesSizeAferFinishing = scoreBoard.getMatches().size();
        assertTrue(isMatchFinished);
        assertEquals(0, matchesSizeAferFinishing);
    }

    @Test
    public void finishMatchWithInvalidTeamShouldFail() {

        scoreBoard.startAMatch("new york", "barcelona");

        boolean isMatchFinished = scoreBoard.finishMatch("malaga");
        int matchesSizeAferFinishing = scoreBoard.getMatches().size();
        assertFalse(isMatchFinished);
        assertEquals(1, matchesSizeAferFinishing);

    }

    //Return summary
    @Test
    public void returnSummary_Success() {

        scoreBoard.startAMatch("new york", "barcelona");
        scoreBoard.startAMatch("malaga", "rome");
        scoreBoard.startAMatch("pekin", "london");
        scoreBoard.startAMatch("sydney", "keystone");
        scoreBoard.startAMatch("melburn", "kyiv");

        scoreBoard.updateScore("barcelona", 1, 0);
        scoreBoard.updateScore("barcelona", 2, 0);
        scoreBoard.updateScore("malaga", 0, 1);
        scoreBoard.updateScore("malaga", 1, 1);
        scoreBoard.updateScore("pekin", 1, 0);

        scoreBoard.finishMatch("kyiv");

        String etalonSummary = "\t\t\t\tSUMMARY\n" +
                "\n" +
                "\t\t1. malaga 1 - rome 1\n" +
                "\t\t2. new york 2 - barcelona 0\n" +
                "\t\t3. pekin 1 - london 0\n" +
                "\t\t4. sydney 0 - keystone 0\n";

        String actualSummary = scoreBoard.getSummary();
        assertEquals(etalonSummary, actualSummary);
    }
}
