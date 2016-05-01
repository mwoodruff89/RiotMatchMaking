/**
 * Created by michaelwoodruff on 1/5/2016.
 */
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({EloTests.class, MatchMakerTests.class, MatchTests.class, PlayerTests.class, WinRatioTests.class})

public class TestSuite {
}
