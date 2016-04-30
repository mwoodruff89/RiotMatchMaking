package com.riotgames.interview.hongkong.matchmaking;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
/**
 * The matchmaking implementation that you will write.
 */
public class MatchmakerImpl implements Matchmaker {

    /**
     * Data structure to contain matches still not fully matched
     */
    private final ArrayList<Match> matchingMatches = new ArrayList<Match>();

    /**
     * Data structure containing all the matches with players inside which are already fully matched
     */
    private final ArrayList<Match> fullyMatchedMatches = new ArrayList<Match>();

    /**
     * The maximum amount of players per team for this MatchmakerImpl instance
     */
    private int playersPerTeam = 0;

    public Match findMatch(int playersPerTeam) {

        this.playersPerTeam = playersPerTeam;

        Match matchToFind = null;

        List<Player> playerList = SampleData.getPlayers();

        //Add all players to the match making data structure
        for (Player player : playerList) {

            enterMatchmaking(player);
            if(!fullyMatchedMatches.isEmpty()) {

                matchToFind = fullyMatchedMatches.get(0);
                break;
            }
        }

        return matchToFind;
    }

    public void enterMatchmaking(Player player) {

        //Find out if we can add the player to any of the matches currently trying to match
        for (Match match : new ArrayList<Match>(matchingMatches)) {

            if (match.canAddWithRating(player.getWinRatio())) {

                //We can add a player to a match so add him.
                match.addPlayer(player);
                if(match.isFullyMatched) {

                    //If that match is fully matched, we should update MatchMaking data structures accordingly
                    matchingMatches.remove(match);
                    fullyMatchedMatches.add(match);
                }

                return;
            }
        }

        //We didn't find a match for the player so let's create a match for him and add him to team 1
        HashSet<Player> newTeam = new HashSet<Player>();
        newTeam.add(player);
        Match newMatch = new Match(newTeam, new HashSet<Player>(), playersPerTeam);
        matchingMatches.add(newMatch);
    }
}
