import com.riotgames.interview.hongkong.matchmaking.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by michaelwoodruff on 30/4/2016.
 */
public class EloTests {

    Matchmaker testableMatchMaker = null;
    Match matchedMatch = null;

    @Before
    public void setup() {

        testableMatchMaker = new MatchmakerImpl();
        matchedMatch = null;
    }

    @Test
    /**
     * Given a team size of 1
     * When we request to make a 1v1
     * Then the match flag should be updated to fully matched
     * And all players must be within the required Elo range
     * And all team sizes must equal 1
     */
    public void testMatch1v1Elo() {

        int teamSize = 1;
        matchedMatch = testableMatchMaker.findMatch(teamSize);
        //Test isFullyMatched flag is updated correctly
        assert (matchedMatch.isFullyMatched);

        assetAllPlayersHaveValidEloRatingInTeam(matchedMatch.getTeam1());
        assetAllPlayersHaveValidEloRatingInTeam(matchedMatch.getTeam2());
        //Test all team sizes are correct
        assert (matchedMatch.getTeam1().teamSize() == teamSize);
        assert (matchedMatch.getTeam2().teamSize() == teamSize);
    }

    @Test
    /**
     * Given a team size of 2
     * When we request to make a 2v2
     * Then the match flag should be updated to fully matched
     * And all players must be within the required Elo range
     * And all team sizes must equal 2
     */
    public void testMatch2v2Elo() {

        int teamSize = 2;
        matchedMatch = testableMatchMaker.findMatch(teamSize);
        assert (matchedMatch.isFullyMatched);

        assert (matchedMatch.getTeam1().teamSize() == teamSize);
        assert (matchedMatch.getTeam2().teamSize() == teamSize);

        //Test all players in the team are within the rating
        assetAllPlayersHaveValidEloRatingInTeam(matchedMatch.getTeam1());
        assetAllPlayersHaveValidEloRatingInTeam(matchedMatch.getTeam1());
    }

    @Test
    /**
     * Given a team size of 3
     * When we request to make a 3v3
     * Then the match flag should be updated to fully matched
     * And all players must be within the required Elo range
     * And all team sizes must equal 3
     */
    public void testMatch3v3Elo() {

        int teamSize = 3;
        matchedMatch = testableMatchMaker.findMatch(teamSize);
        assert (matchedMatch.isFullyMatched);

        assert (matchedMatch.getTeam1().teamSize() == teamSize);
        assert (matchedMatch.getTeam2().teamSize() == teamSize);

        assetAllPlayersHaveValidEloRatingInTeam(matchedMatch.getTeam1());
        assetAllPlayersHaveValidEloRatingInTeam(matchedMatch.getTeam2());
    }

    @Test
    /**
     * Given a team size of 4
     * When we request to make a 4v4
     * Then the match flag should be updated to fully matched
     * And all players must be within the required Elo range
     * And all team sizes must equal 4
     */
    public void testMatch4v4Elo() {

        int teamSize = 4;
        matchedMatch = testableMatchMaker.findMatch(teamSize);
        assert (matchedMatch.isFullyMatched);

        assert (matchedMatch.getTeam1().teamSize() == teamSize);
        assert (matchedMatch.getTeam2().teamSize() == teamSize);

        assetAllPlayersHaveValidEloRatingInTeam(matchedMatch.getTeam1());
        assetAllPlayersHaveValidEloRatingInTeam(matchedMatch.getTeam2());
    }

    @Test
    /**
     * Given a team size of 5
     * When we request to make a 5v5
     * Then the match flag should be updated to fully matched
     * And all players must be within the required Elo range
     * And all team sizes must equal 5
     */
    public void testMatch5v5Elo() {

        int teamSize = 5;
        matchedMatch = testableMatchMaker.findMatch(teamSize);
        assert (matchedMatch.isFullyMatched);

        assert (matchedMatch.getTeam1().teamSize() == teamSize);
        assert (matchedMatch.getTeam2().teamSize() == teamSize);

        assetAllPlayersHaveValidEloRatingInTeam(matchedMatch.getTeam1());
        assetAllPlayersHaveValidEloRatingInTeam(matchedMatch.getTeam2());
    }

    /**
     * Given a team of players, will assert if every player within that team is within
     * its' matches rating range
     *
     * @param team - The team which will be tested to see if every team member is within the maximum rating difference
     */
    protected void assertAllPlayersHaveValidWinRatingInTeam(Team team) {

        for (Player player : team.getPlayers()) {

            double playerToMatchDifference = player.getWinRatio() - matchedMatch.averageRating;
            assert (Math.abs(playerToMatchDifference) < matchedMatch.maxDifference);

            double teamToMatchRatingDifference = team.getAverageWinRating() - matchedMatch.averageRating;
            assert (Math.abs(teamToMatchRatingDifference) < matchedMatch.maxDifference);
        }
    }

    /**
     * Given a team of players, this function will assert if every player within that team is within its'matches rating range
     * @param team - The team which will be tested to see if every team member is within the maximum rating difference
     */
    protected void assetAllPlayersHaveValidEloRatingInTeam(Team team) {

        for (Player player : team.getPlayers()) {

            double playerToMatchDifference = player.getEloRating() - matchedMatch.averageRating;
            assert (Math.abs(playerToMatchDifference) < matchedMatch.maxDifference);

            double playerToMatchRatingDifference = team.getAverageElo() -matchedMatch.averageRating;
            assert (Math.abs(playerToMatchRatingDifference) < matchedMatch.maxDifference);
        }
    }
}
