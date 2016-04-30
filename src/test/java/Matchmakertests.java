import com.riotgames.interview.hongkong.matchmaking.Matchmaker;
import org.junit.Before;
import org.junit.Test;
import com.riotgames.interview.hongkong.matchmaking.MatchmakerImpl;
import com.riotgames.interview.hongkong.matchmaking.Match;
import com.riotgames.interview.hongkong.matchmaking.Player;
import com.riotgames.interview.hongkong.matchmaking.Team;
import com.riotgames.interview.hongkong.matchmaking.Game;

import java.util.ArrayList;
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

    @Test
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
    public void testMatch5v5Elo() {

        int teamSize = 5;
        matchedMatch = testableMatchMaker.findMatch(teamSize);
        assert (matchedMatch.isFullyMatched);

        assert (matchedMatch.getTeam1().teamSize() == teamSize);
        assert (matchedMatch.getTeam2().teamSize() == teamSize);

        assetAllPlayersHaveValidEloRatingInTeam(matchedMatch.getTeam1());
        assetAllPlayersHaveValidEloRatingInTeam(matchedMatch.getTeam2());
    }

    @Test
    public void testGame() {

        int teamSize = 5;
        ArrayList<Match> matchedMatches = testableMatchMaker.findMatches(5);

        for (Match match : matchedMatches) {

            Game newGame = new Game(match);
            newGame.playMatch();
        }
    }

    /**
     * Given a set which represents a team of players, will assert if every player within that team is within
     * its' matches rating range
     *
     * @param team - The team which will be tested to see if every team member is within the maximum rating difference
     */
    private void assertAllPlayersHaveValidWinRatingInTeam(Team team) {

        for (Player player : team.getPlayers()) {

            double playerToMatchDifference = player.getWinRatio() - matchedMatch.averageRating;
            assert (Math.abs(playerToMatchDifference) < matchedMatch.maxDifference);

            double teamToMatchRatingDifference = team.getAverageWinRating() - matchedMatch.averageRating;
            assert (Math.abs(teamToMatchRatingDifference) < matchedMatch.maxDifference);
        }
    }

    private void assetAllPlayersHaveValidEloRatingInTeam(Team team) {

        for (Player player : team.getPlayers()) {

            double playerToMatchDifference = player.getEloRating() - matchedMatch.averageRating;
            assert (Math.abs(playerToMatchDifference) < matchedMatch.maxDifference);

            double playerToMatchRatingDifference = team.getAverageElo() -matchedMatch.averageRating;
            assert (Math.abs(playerToMatchRatingDifference) < matchedMatch.maxDifference);
        }
    }
}
