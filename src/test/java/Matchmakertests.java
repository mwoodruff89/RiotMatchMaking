import org.junit.Test;
import com.riotgames.interview.hongkong.matchmaking.MatchmakerImpl;
import com.riotgames.interview.hongkong.matchmaking.Match;
/**
 * Created by michaelwoodruff on 29/4/2016.
 */
public class Matchmakertests {

    @Test
    public void testMatch1v1() {

        MatchmakerImpl matchmaker = new MatchmakerImpl();
        Match oneVOneMatch = matchmaker.findMatch(1);
        assert(oneVOneMatch.isFullyMatched);
    }
}
