import com.riotgames.interview.hongkong.matchmaking.MatchmakerImpl;
import com.riotgames.interview.hongkong.matchmaking.Match;
import com.riotgames.interview.hongkong.matchmaking.Player;
import com.riotgames.interview.hongkong.matchmaking.SampleData;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Tests the various functions within the MatchmakerImpl class
 */
public class MatchMakerTests {

    MatchmakerImpl testableMatchMaker = null;

    @Before
    public void setup() {

        SampleData.resetPlayers();
        SampleData.getPlayers();
        testableMatchMaker = new MatchmakerImpl();
    }

    @Test
    /**
     * Given a match maker finds a 5v5
     * When the match maker plays the matches' game
     * Then the match should be played, moved to the completed list
     * And all the players isMatched status should be false
     */
    public void testMatchSingleEloIsSorted() {

        //Given
        Match testableMatch = testableMatchMaker.findMatchWithRuleAndIsSorted(5, MatchmakerImpl.MatchMakingRule.Elo, true);

        //When
        testableMatchMaker.playMatch(testableMatch);

        //Then
        assert (testableMatchMaker.getCompletedMatches().contains(testableMatch));
        for(Player player : testableMatch.getAllPlayers()) {

            assert (!player.getIsMatched());
        }
    }

    /**
     * Given a match maker finds a set of 5v5 matches
     * When the match maker plays all of these games
     * Then all matches should be played and moved to the completed list
     * And all the players isMatched status should be false
     */
    public void testMatchSingleWRIsSorted() {

        //Given
        ArrayList<Match> testableMatches = testableMatchMaker.findMatchesWithRuleAndIsSorted(5, MatchmakerImpl.MatchMakingRule.Elo, true);

        //When
        testableMatchMaker.playMatches();

        //Then
        for(Match testableMatch : testableMatches) {

            assert (testableMatchMaker.getCompletedMatches().contains(testableMatch));
            for(Player player : testableMatch.getAllPlayers()) {

                assert (!player.getIsMatched());
            }
        }
    }

    /**
     * Given a matchmaker and a new player which is not yet matched
     * When I add the new player to the matchmaker's queue using enterMatchMaking
     * Then that player should then its' status updated
     */
    public void testValidPlayerIsAdded() {

        //Given
        Player newPlayer = new Player("Mike Woodruff", 123, 50);
        assert (!newPlayer.getIsMatched());

        //When
        testableMatchMaker.enterMatchmaking(newPlayer);

        //Then
        assert (newPlayer.getIsMatched());
    }
}
