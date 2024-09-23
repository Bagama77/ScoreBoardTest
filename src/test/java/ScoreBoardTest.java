import org.junit.Before;
import org.junit.Test;

public class ScoreBoardTest {

    @Before
    public void setup() {
    }

    //Start match
    @Test
    public void startMatchWithTeamsSuccess() {
    }

    @Test
    public void startMatchWithOneTeamNullShouldDenied() {
    }

    @Test
    public void startMatchWithTwoSameTeamsShouldDenied() {
    }

    @Test
    public void startMatchWithTwoTeamsNullShouldDenied() {
    }

    @Test
    public void startMatchWithTwoTeamsWithEmptyNamesShouldDenied() {
    }

    //Update score
    @Test
    public void updateScoreWithValidNewScores_Success() {
    }

    @Test
    public void updateScoreWithTeamNotInBoardShouldFail() {
    }

    @Test
    public void updateScoreWithHomeTeamValidNewScores_Success() {
    }

    @Test
    public void updateScoreWithGuestTeamValidNewScores_Success() {
    }

    @Test
    public void updateScoreWithInvalidNewScoresShouldFail() {
    }

    //Finish match

    @Test
    public void finishMatchWithValidTeam_Success() {
    }

    @Test
    public void finishMatchWithInvalidTeamShouldFail() {
    }

    //Return summary
    @Test
    public void returnSummary_Success() {
    }
}
