import com.riotgames.interview.hongkong.matchmaking.MatchmakerImpl;
import com.riotgames.interview.hongkong.matchmaking.Player;
import com.riotgames.interview.hongkong.matchmaking.Match;
import com.riotgames.interview.hongkong.matchmaking.SampleData;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

/**
 * Created by michaelwoodruff on 1/5/2016.
 */
public class MatchTests {

    Match testMatch;

    @Before
    public void setup() {

        SampleData.resetPlayers();
        SampleData.getPlayers();
    }

    @Test
    /**
     * Given a 5v5 match which is full
     * When canAddPlayer is called with a given player
     * Then it should return false
     */
    public void testCanAddFullMatch() {

        setupTestMatch(5, 5, 5, MatchmakerImpl.MatchMakingRule.Elo);
        Player testPlayer = new Player("Test Player", 100, 100);
        assert (!testMatch.canAddPlayer(testPlayer));
    }

    @Test
    /**
     * Given a 5v5 match which is not full
     * When canAddPlayer is called with a given player
     * Then it should return true
     */
    public void testCanAddToNotFullMatch() {

        setupTestMatch(5, 4, 5, MatchmakerImpl.MatchMakingRule.Elo);
        Player testPlayer = new Player("Test Player", 100, 100);
        assert (testMatch.canAddPlayer(testPlayer));
    }

    @Test
    /**
     * Given a 5v5 match which is not full
     * When a given player is added to the match using addPlayer(Player)
     * Then the total number of players shoud increment by one
     */
    public void testAdd() {

        setupTestMatch(4, 4, 5, MatchmakerImpl.MatchMakingRule.Elo);
        Player testPlayer = new Player("Test Player", 100, 100);

        int sizeBefore = testMatch.getAllPlayers().size();
        testMatch.addPlayer(testPlayer);

        assert (testMatch.getAllPlayers().size() == (sizeBefore + 1));
    }

    @Test
    /**
     * Given a non full match using the Elo match making rule
     * When a new player is added to the match
     * Then the elo average rating should be updated to the new average (mean)
     */
    public void testEloAverageRatingAfterAdd() {

        setupTestMatch(4, 4, 5, MatchmakerImpl.MatchMakingRule.Elo);
        Player testPlayer = new Player("Test Player", 100, 100);

        testMatch.addPlayer(testPlayer);

        double totalEloRating = 0;
        for(Player player: testMatch.getAllPlayers()) {

            totalEloRating += player.getEloRating();
        }

        double averageEloRating = totalEloRating / testMatch.getAllPlayers().size();

        assert (averageEloRating == testMatch.averageRating);
    }

    @Test
    /**
     * Given a non full match using the WR match making rule
     * When a new player is added to the match
     * Then the WR average rating should be updated to the new average (mean)
     */
    public void testWRAverageRatingAfterAdd() {

        setupTestMatch(4, 4, 5, MatchmakerImpl.MatchMakingRule.WinRatio);
        Player testPlayer = new Player("Test Player", 100, 100);

        testMatch.addPlayer(testPlayer);

        double totalWRRating = 0;
        for(Player player: testMatch.getAllPlayers()) {

            totalWRRating += player.getWinRatio();
        }

        double averageWRRating = totalWRRating / testMatch.getAllPlayers().size();

        assert (averageWRRating == testMatch.averageRating);
    }

    /**
     * Helper method to help set up test matches
     */
    private void setupTestMatch(int t1Size, int t2Size, int teamSize, MatchmakerImpl.MatchMakingRule rule) {

        HashSet<Player> team1 = new HashSet<Player>();
        for(int i = 0; i < t1Size; i++) {

            Player p = new Player("Player " + i, 100, 100);
            team1.add(p);
        }

        HashSet<Player> team2 = new HashSet<Player>();
        for(int i = 0; i < t2Size; i++) {

            Player p = new Player("Player " + (i + t1Size), 100, 100);
            team2.add(p);
        }

        MatchmakerImpl dummyMatchmaker = new MatchmakerImpl();
        dummyMatchmaker.matchingRule = rule;
        testMatch = new Match(team1, team2, teamSize, dummyMatchmaker);
    }
}
