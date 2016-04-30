import org.junit.Test;
import com.riotgames.interview.hongkong.matchmaking.MatchmakerImpl;

/**
 * Created by michaelwoodruff on 29/4/2016.
 */
public class WinRatioTests extends EloTests {

    @Test
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
    public void testMatch2v2() {

        int teamSize = 2;
        matchedMatch = testableMatchMaker.findMatchWithRule(teamSize, MatchmakerImpl.MatchMakingRule.WinRatio);
        assert (matchedMatch.isFullyMatched);

        assert (matchedMatch.getTeam1().teamSize() == teamSize);
        assert (matchedMatch.getTeam2().teamSize() == teamSize);

        //Test all players in the team are within the rating
        assertAllPlayersHaveValidWinRatingInTeam(matchedMatch.getTeam1());
        assertAllPlayersHaveValidWinRatingInTeam(matchedMatch.getTeam1());
    }

    @Test
    public void testMatch3v3() {

        int teamSize = 3;
        matchedMatch = testableMatchMaker.findMatchWithRule(teamSize, MatchmakerImpl.MatchMakingRule.WinRatio);
        assert (matchedMatch.isFullyMatched);

        assert (matchedMatch.getTeam1().teamSize() == teamSize);
        assert (matchedMatch.getTeam2().teamSize() == teamSize);

        assertAllPlayersHaveValidWinRatingInTeam(matchedMatch.getTeam1());
        assertAllPlayersHaveValidWinRatingInTeam(matchedMatch.getTeam2());
    }

    @Test
    public void testMatch4v4() {

        int teamSize = 4;
        matchedMatch = testableMatchMaker.findMatchWithRule(teamSize, MatchmakerImpl.MatchMakingRule.WinRatio);
        assert (matchedMatch.isFullyMatched);

        assert (matchedMatch.getTeam1().teamSize() == teamSize);
        assert (matchedMatch.getTeam2().teamSize() == teamSize);

        assertAllPlayersHaveValidWinRatingInTeam(matchedMatch.getTeam1());
        assertAllPlayersHaveValidWinRatingInTeam(matchedMatch.getTeam2());
    }

    @Test
    public void testMatch5v5() {

        int teamSize = 5;
        matchedMatch = testableMatchMaker.findMatchWithRule(teamSize, MatchmakerImpl.MatchMakingRule.WinRatio);
        assert (matchedMatch.isFullyMatched);

        assert (matchedMatch.getTeam1().teamSize() == teamSize);
        assert (matchedMatch.getTeam2().teamSize() == teamSize);

        assertAllPlayersHaveValidWinRatingInTeam(matchedMatch.getTeam1());
        assertAllPlayersHaveValidWinRatingInTeam(matchedMatch.getTeam2());
    }
}
