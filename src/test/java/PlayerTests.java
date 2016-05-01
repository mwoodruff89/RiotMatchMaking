import com.riotgames.interview.hongkong.matchmaking.MatchmakerImpl;
import com.riotgames.interview.hongkong.matchmaking.Player;
import com.riotgames.interview.hongkong.matchmaking.SampleData;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by michaelwoodruff on 1/5/2016.
 */
public class PlayerTests {

    @Before
    public void setup() {

        SampleData.resetPlayers();
        SampleData.getPlayers();
    }

    @Test
    /**
     * Given I want to create a player
     * When I add a player with negative wins
     * Then wins are set to zero
     */
    public void testInvalidLossData() {

        Player testPlayer = new Player("Test Player", -100, 100);
        assert (testPlayer.getWins() == 0);
    }

    @Test
    /**
     * Given I want to create a player
     * When I add a player with negative losses
     * Then losses are set to zero
     */
    public void testInvalidWinData() {

        Player testPlayer = new Player("Test Player", 100, -100);
        assert (testPlayer.getLosses() == 0);
    }

    @Test
    /**
     * Given I create a player with given wins and losses x and y respectively
     * When I initialise the player
     * Then the win ratio should be such that WR = wins / (wins + losses)
     */
    public void testWinRatio() {

        Player averagePlayer = new Player("Test Player 1", 100, 100);
        assert (averagePlayer.getWinRatio() == 0.5);

        Player badPlayer = new Player("Test Player 2", 25, 75);
        assert (badPlayer.getWinRatio() == 0.25);

        Player goodPlayer = new Player("Test Player 3",75, 25);
        assert (goodPlayer.getWinRatio() == 0.75);
    }
}
