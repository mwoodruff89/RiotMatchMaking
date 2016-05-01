import com.riotgames.interview.hongkong.matchmaking.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * This test class runs tests to ensure the Elo matching algorithm matches players within the desired range
 * and that matches are the correct size.
 */
public class EloTests {

    Matchmaker testableMatchMaker = null;
    Match matchedMatch = null;

    @Before
    public void setup() {

        SampleData.resetPlayers();
        SampleData.getPlayers();
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

        //Test all team sizes are correct
        assertValidEloRatingInTeam(matchedMatch.getTeam1(), matchedMatch.getTeam2());
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
        assertValidEloRatingInTeam(matchedMatch.getTeam1(), matchedMatch.getTeam2());
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

        assertValidEloRatingInTeam(matchedMatch.getTeam1(), matchedMatch.getTeam2());
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

        assertValidEloRatingInTeam(matchedMatch.getTeam1(), matchedMatch.getTeam2());
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

        assertValidEloRatingInTeam(matchedMatch.getTeam1(), matchedMatch.getTeam2());
    }

    /**
     * Given a team of players, will assert if every player within that team is within
     * its' matches rating range
     *
     * Note: The +1 is to account to round down division when calculating average ratings
     * @param team1 - The first team in the match
     * @param team2 - The second team in the match
     * */
    protected void assertValidWinRatingInTeam(Team team1, Team team2) {

        double team1ToMatchRatingDifference = team1.getAverageWinRating() -matchedMatch.averageRating;
        assert (Math.abs(team1ToMatchRatingDifference) <= matchedMatch.maxDifference + 1);

        double team2ToMatchRatingDifference = team2.getAverageWinRating() -matchedMatch.averageRating;
        assert (Math.abs(team2ToMatchRatingDifference) <= matchedMatch.maxDifference + 1);

        double team1ToTeam2MatchRatingDifference = team1.getAverageWinRating() - team2.getAverageWinRating();
        assert (Math.abs(team1ToTeam2MatchRatingDifference) <= matchedMatch.maxDifference + 1);
    }

    /**
     * Given a team of players, this function will assert if every player within that team is within its'matches rating range
     * Note: The +1 is to account to round down division when calculating average ratings
     * @param team1 - The first team in the match
     * @param team2 - The second team in the match
     */
    protected void assertValidEloRatingInTeam(Team team1, Team team2) {

        double team1ToMatchRatingDifference = team1.getAverageElo() - matchedMatch.averageRating;
        assert (Math.abs(team1ToMatchRatingDifference) <= matchedMatch.maxDifference + 1);

        double team2ToMatchRatingDifference = team2.getAverageElo() - matchedMatch.averageRating;
        assert (Math.abs(team2ToMatchRatingDifference) <= matchedMatch.maxDifference + 1);

        double team1ToTeam2MatchRatingDifference = team1.getAverageElo() - team2.getAverageElo();
        assert (Math.abs(team1ToTeam2MatchRatingDifference) <= matchedMatch.maxDifference + 1);
    }
}
