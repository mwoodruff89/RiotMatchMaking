import com.riotgames.interview.hongkong.matchmaking.Matchmaker;
import org.junit.Before;
import org.junit.Test;
import com.riotgames.interview.hongkong.matchmaking.MatchmakerImpl;
import com.riotgames.interview.hongkong.matchmaking.Match;
import com.riotgames.interview.hongkong.matchmaking.Player;
import java.util.Set;
import java.util.HashSet;

/**
 * Created by michaelwoodruff on 29/4/2016.
 */
public class Matchmakertests {

    Matchmaker testableMatchMaker = null;
    Match matchedMatch = null;

    @Before
    public void setup() {

        testableMatchMaker = new MatchmakerImpl();
        matchedMatch = null;
    }

    @Test
    public void testMatch1v1() {

        int teamSize = 1;
        matchedMatch = testableMatchMaker.findMatch(teamSize);
        //Test isFullyMatched flag is updated correctly
        assert (matchedMatch.isFullyMatched);

        //Test all team sizes are correct
        assert (matchedMatch.getTeam1().size() == teamSize);
        assert (matchedMatch.getTeam2().size() == teamSize);
    }

    @Test
    public void testMatch2v2() {

        int teamSize = 2;
        matchedMatch = testableMatchMaker.findMatch(teamSize);
        assert (matchedMatch.isFullyMatched);

        assert (matchedMatch.getTeam1().size() == teamSize);
        assert (matchedMatch.getTeam2().size() == teamSize);

        //Test all players in the team are within the rating
        assertAllPlayersHaveValidRating(matchedMatch.getTeam1());
        assertAllPlayersHaveValidRating(matchedMatch.getTeam1());
    }

    @Test
    public void testMatch3v3() {

        int teamSize = 3;
        matchedMatch = testableMatchMaker.findMatch(teamSize);
        assert (matchedMatch.isFullyMatched);

        assert (matchedMatch.getTeam1().size() == teamSize);
        assert (matchedMatch.getTeam2().size() == teamSize);

        assertAllPlayersHaveValidRating(matchedMatch.getTeam1());
        assertAllPlayersHaveValidRating(matchedMatch.getTeam2());
    }

    @Test
    public void testMatch4v4() {

        int teamSize = 4;
        matchedMatch = testableMatchMaker.findMatch(teamSize);
        assert (matchedMatch.isFullyMatched);

        assert (matchedMatch.getTeam1().size() == teamSize);
        assert (matchedMatch.getTeam2().size() == teamSize);

        assertAllPlayersHaveValidRating(matchedMatch.getTeam1());
        assertAllPlayersHaveValidRating(matchedMatch.getTeam2());
    }

    @Test
    public void testMatch5v5() {

        int teamSize = 5;
        matchedMatch = testableMatchMaker.findMatch(teamSize);
        assert (matchedMatch.isFullyMatched);

        assert (matchedMatch.getTeam1().size() == teamSize);
        assert (matchedMatch.getTeam2().size() == teamSize);

        assertAllPlayersHaveValidRating(matchedMatch.getTeam1());
        assertAllPlayersHaveValidRating(matchedMatch.getTeam2());
    }

    /**
     * Given a set which represents a team of players, will assert if every player within that team is within
     * its' matches rating range
     * @param team - The team which will be tested to see if every team member is within the maximum rating difference
     */
    private void assertAllPlayersHaveValidRating(Set<Player> team) {

        for (Player player : new HashSet<Player>(team)) {

            double ratingDifference = player.getWinRatio() - matchedMatch.averageWinRating;
            assert (Math.abs(ratingDifference) < matchedMatch.kMaxDifference);
        }
    }
}
