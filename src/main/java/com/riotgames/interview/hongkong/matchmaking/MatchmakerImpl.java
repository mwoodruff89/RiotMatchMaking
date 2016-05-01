package com.riotgames.interview.hongkong.matchmaking;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
/**
 * The matchmaking implementation that you will write.
 */
public class MatchmakerImpl implements Matchmaker {

    /**
     * Defines two different match making rules; WinRatio and Elo
     * WinRatio
     * Matchups are found based on the player's win ratio. (wins / (losses + wins)).
     * Winning probabilities for WR matches are calculated by:
     * Probability of T1 Win = T1 Average WR / (T1 Average WR + T2 Average WR)
     *
     * ELO
     * Matchups are found based on the players Elo ranking. By default players enter the ladder at 1000 points.
     * Because the sample data already has wins and losses (but we don't know WHO the player won / lost against), we use
     * a simple formula to guess the player's Elo.
     *
     * Then, the Elo formula defined in in Game.java is utilised to calculate winning probabilities and how many points
     * the winning / losing team gain / lose following a completed game
     */
    public enum MatchMakingRule {

        WinRatio, Elo;
    }

    /**
     * Data structure to contain matches still not fully matched
     */
    private final ArrayList<Match> matchingMatches = new ArrayList<Match>();

    /**
     * Data structure containing all the matches with players inside which are already fully matched
     */
    private final ArrayList<Match> fullyMatchedMatches = new ArrayList<Match>();

    /**
     * History of completed matches in this match making system
     */
    private ArrayList<Match> completedMatches = new ArrayList<Match>();

    /**
     * History of cancelled matches in this match making system
     */
    private ArrayList<Match> cancelledMatches = new ArrayList<Match>();

    /**
     * The maximum amount of players per team for this MatchmakerImpl instance
     */
    private int playersPerTeam = 0;

    /**
     * The maximum difference between player's Elo rating
     */
    private double maxEloDifference = 20;

    /**
     * The list of players we will attempt to match against eachother in this match maker system
     */
    private List<Player> playerList;

    /**
     * The match making rule / algorithm to be utilised. Default is Elo but can be edited to use a different rule
     */
    public MatchmakerImpl.MatchMakingRule matchingRule;

    //Getters
    public ArrayList<Match>getCompletedMatches() { return completedMatches; }
    public double getMaxEloDifference() { return this.maxEloDifference; }

    public Match findMatch(int playersPerTeam) {

        return findMatchWithRule(playersPerTeam, MatchMakingRule.Elo);
    }

    public Match findMatchWithRule(int playersPerTeam, MatchmakerImpl.MatchMakingRule rule) {

        return findMatchWithRuleAndIsSorted(playersPerTeam, rule, true);
    }

    public Match findMatchWithRuleAndIsSorted(int playersPerTeam, MatchmakerImpl.MatchMakingRule rule, Boolean isSorted) {

        this.playersPerTeam = playersPerTeam;
        this.matchingRule = rule;
        Match matchToFind = null;

        if(isSorted) {

            if (this.matchingRule == MatchMakingRule.WinRatio) {

                playerList = SampleData.getPlayersSortedByWinRatio();
            } else {

                playerList = SampleData.getPlayersSortedByElo();
            }
        } else {

            playerList = SampleData.getPlayers();
        }

        //Add all players to the match making data structure
        for (Player player : playerList) {

            if(!player.getIsMatched()) {

                enterMatchmaking(player);
                player.setIsMatched(true);
                if (!fullyMatchedMatches.isEmpty()) {

                    //Note: break and return here as this function returns a Single match. Therefore just return the first
                    //found match
                    matchToFind = fullyMatchedMatches.get(0);
                    break;
                }
            }
        }

        return matchToFind;
    }

    public ArrayList<Match> findMatches(int playersPerTeam) {

        return findMatchesWithRule(playersPerTeam, MatchMakingRule.Elo);
    }

    public ArrayList<Match> findMatchesWithRule(int playersPerTeam, MatchmakerImpl.MatchMakingRule rule) {

        return findMatchesWithRuleAndIsSorted(playersPerTeam, rule, true);
    }

    public ArrayList<Match> findMatchesWithRuleAndIsSorted(int playersPerTeam, MatchmakerImpl.MatchMakingRule rule, Boolean isSorted) {

        this.playersPerTeam = playersPerTeam;
        this.matchingRule = rule;

        if(isSorted) {

            if (this.matchingRule == MatchMakingRule.WinRatio) {

                playerList = SampleData.getPlayersSortedByWinRatio();
            } else {

                playerList = SampleData.getPlayersSortedByElo();
            }
        } else {

            playerList = SampleData.getPlayers();
        }

        for (Player player : playerList) {

            if(!player.getIsMatched()) {

                enterMatchmaking(player);
                player.setIsMatched(true);
            }
        }

        if(fullyMatchedMatches.size() == 0 && matchingMatches.size() > 0) {

            resetMatchMaking();
        }
        return fullyMatchedMatches;
    }

    public void enterMatchmaking(Player player) {

        //Find out if we can add the player to any of the matches currently trying to match
        for (Match match : new ArrayList<Match>(matchingMatches)) {

            Boolean canAdd = false;
            if(matchingRule == MatchMakingRule.WinRatio) {

                canAdd = match.canAddPlayer(player);
            } else {

                canAdd = match.canAddPlayer(player);
            }
            if (canAdd) {

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
        Match newMatch = new Match(newTeam, new HashSet<Player>(), playersPerTeam, this);

        if (matchingRule == MatchMakingRule.Elo) {

            newMatch.setkMaxDifferenceElo(maxEloDifference);
        }
        matchingMatches.add(newMatch);
    }

    /**
     * Given that the match is in the fully matched matches set, will proeed to play the matches' game.
     *
     * @param match - The match to be played
     */
    public void playMatch(Match match) {

        if(fullyMatchedMatches.contains(match)) {

            System.out.println(match);
            System.out.println(match.getGame());
            match.playMatch();
            System.out.println(match.getGame());
            didFinishRound();
        } else {

            System.out.print("\n Cannot play this match as it is not fully matched");
        }
    }

    /**
     * Given that there is a non empty set of fully matched matches, it will proceed to play those matches' games.
     */
    public void playMatches() {

        for (Match match : fullyMatchedMatches) {

            System.out.println(match);
            System.out.println(match.getGame());
            match.playMatch();
            System.out.println(match.getGame());
        }

        didFinishRound();
    }

    /**
     * Data handler method. Moves all the matched matches to the completed list and updates the players times. Should be
     * called following a set of games being played
     */
    private void didFinishRound() {

        //Move the matches between queus and update the player data to not match and update their waiting times.
        for(Match completedMatch : new ArrayList<Match>(fullyMatchedMatches)) {

            for(Player player : completedMatch.getAllPlayers()) {

                player.setIsMatched(false);
                player.resetTimeWaiting();
                player.updateGamesPlayedInSim();
            }

            fullyMatchedMatches.remove(completedMatch);
            completedMatches.add(completedMatch);
        }

        //Update the time waiting for these players
        for (Match matchStillWaiting : new ArrayList<Match>(matchingMatches)) {

            for (Player waitingPlayer : matchStillWaiting.getAllPlayers()) {

                waitingPlayer.updateTimeWaiting();
            }
        }
    }

    /**
     * This method will reset the match making process by:
     * 1. Releasing all players from the match they are in (set their is matched to false)
     * 2. remove cancelled match from matchingMatches and into cancelled matches
     * 3. Update the default max elo difference from x to x + 20
     *
     * Why does this happen?
     *
     * Because every player is inside a match, but there are no more players that are free. Basically, all matches are in
     * a 'trying' to match state but there's no more players
     *
     * This mainly happens because:
     * 1. The player pool is very small (only 200) and players are now very spread out in terms of skill
     * 2. The default 'max Elo' difference of (20 + offset) is also too small considering how 'spread' the players elo ratings are
     */
    private void resetMatchMaking() {

        for (Match match : new ArrayList<Match>(matchingMatches)) {

            for (Player cancelledPlayer : match.getAllPlayers()) {

                cancelledPlayer.setIsMatched(false);
            }

            matchingMatches.remove(match);
            cancelledMatches.add(match);
        }

        maxEloDifference += 20;
    }
}