import org.junit.Test;
import com.riotgames.interview.hongkong.matchmaking.MatchmakerImpl;

/**
 * This test class runs tests to ensure the Win Rating matching algorithm matches players within the desired range
 * and that matches are the correct size.
 */
public class WinRatioTests extends EloTests {

    @Test
    /**
     * Given a team size of 1
     * When we request to make a 1v1
     * Then the match flag should be updated to fully matched
     * And all players must be within the required Win Ratio range
     * And all team sizes must equal 1
     */
    public void testMatch1v1() {

        int teamSize = 1;
        matchedMatch = testableMatchMaker.findMatchWithRule(teamSize, MatchmakerImpl.MatchMakingRule.WinRatio);
        //Test isFullyMatched flag is updated correctly
        assert (matchedMatch.isFullyMatched);

        //Test all team sizes are correct
        assert (matchedMatch.getTeam1().teamSize() == teamSize);
        assert (matchedMatch.getTeam2().teamSize() == teamSize);
    }

    @Test
    /**
     * Given a team size of 2
     * When we request to make a 2v2
     * Then the match flag should be updated to fully matched
     * And all players must be within the required Win Ratio range
     * And all team sizes must equal 2
     */
    public void testMatch2v2() {

        int teamSize = 2;
        matchedMatch = testableMatchMaker.findMatchWithRule(teamSize, MatchmakerImpl.MatchMakingRule.WinRatio);
        assert (matchedMatch.isFullyMatched);

        assert (matchedMatch.getTeam1().teamSize() == teamSize);
        assert (matchedMatch.getTeam2().teamSize() == teamSize);

        //Test all players in the team are within the rating
        assertValidWinRatingInTeam(matchedMatch.getTeam1(), matchedMatch.getTeam2());
    }

    @Test
    /**
     * Given a team size of 3
     * When we request to make a 3v3
     * Then the match flag should be updated to fully matched
     * And all players must be within the required Win Ratio range
     * And all team sizes must equal 3
     */
    public void testMatch3v3() {

        int teamSize = 3;
        matchedMatch = testableMatchMaker.findMatchWithRule(teamSize, MatchmakerImpl.MatchMakingRule.WinRatio);
        assert (matchedMatch.isFullyMatched);

        assert (matchedMatch.getTeam1().teamSize() == teamSize);
        assert (matchedMatch.getTeam2().teamSize() == teamSize);

        assertValidWinRatingInTeam(matchedMatch.getTeam1(), matchedMatch.getTeam2());
    }

    @Test
    /**
     * Given a team size of 4
     * When we request to make a 4v4
     * Then the match flag should be updated to fully matched
     * And all players must be within the required Win Ratio range
     * And all team sizes must equal 4
     */
    public void testMatch4v4() {

        int teamSize = 4;
        matchedMatch = testableMatchMaker.findMatchWithRule(teamSize, MatchmakerImpl.MatchMakingRule.WinRatio);
        assert (matchedMatch.isFullyMatched);

        assert (matchedMatch.getTeam1().teamSize() == teamSize);
        assert (matchedMatch.getTeam2().teamSize() == teamSize);

        assertValidWinRatingInTeam(matchedMatch.getTeam1(), matchedMatch.getTeam2());
    }

    @Test
    /**
     * Given a team size of 5
     * When we request to make a 5v5
     * Then the match flag should be updated to fully matched
     * And all players must be within the required Win Ratio range
     * And all team sizes must equal 5
     */
    public void testMatch5v5() {

        int teamSize = 5;
        matchedMatch = testableMatchMaker.findMatchWithRule(teamSize, MatchmakerImpl.MatchMakingRule.WinRatio);
        assert (matchedMatch.isFullyMatched);

        assert (matchedMatch.getTeam1().teamSize() == teamSize);
        assert (matchedMatch.getTeam2().teamSize() == teamSize);

        assertValidWinRatingInTeam(matchedMatch.getTeam1(), matchedMatch.getTeam2());
    }
}
