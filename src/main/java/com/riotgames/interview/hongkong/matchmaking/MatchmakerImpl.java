package com.riotgames.interview.hongkong.matchmaking;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
/**
 * The matchmaking implementation that you will write.
 */
public class MatchmakerImpl implements Matchmaker {

    public enum MatchMakingRule {

        WinRatio, Elo;
    }

    /**
     * Data structure to contain matches still not fully matched
     */
    public final ArrayList<Match> matchingMatches = new ArrayList<Match>();

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

    private double maxEloDifference = 20;

    private List<Player> playerList;
    /**
     * The match making rule / algorithm to be utilised. Default is Elo but can be edited to use a different rule
     */
    public MatchmakerImpl.MatchMakingRule matchingRule;

    public ArrayList<Match>getFullyMatchedMatches() { return fullyMatchedMatches; }

    public ArrayList<Match>getCompletedMatches() { return completedMatches; }

    public Match findMatch(int playersPerTeam) {

        return findMatchWithRule(playersPerTeam, MatchMakingRule.Elo);
    }

    public Match findMatchWithRule(int playersPerTeam, MatchmakerImpl.MatchMakingRule rule) {

        return findMatchWithRuleAndIsSorted(playersPerTeam, rule, true);
    }

    public double getMaxEloDifference() {

        return this.maxEloDifference;
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
     * Data handler method. Moves all the matched matches to the completed list and updates the players times.
     */
    private void didFinishRound() {

        //Move the matches between queus and update the player data to not match and update their waiting times.
        for(Match completedMatch : new ArrayList<Match>(fullyMatchedMatches)) {

            for(Player team1Player : completedMatch.getTeam1().getPlayers()) {

                team1Player.setIsMatched(false);
                team1Player.resetTimeWaiting();
                team1Player.updateGamesPlayedInSim();
            }

            for(Player team2Player : completedMatch.getTeam2().getPlayers()) {

                team2Player.setIsMatched(false);
                team2Player.resetTimeWaiting();
                team2Player.updateGamesPlayedInSim();
            }

            fullyMatchedMatches.remove(completedMatch);
            completedMatches.add(completedMatch);
        }

        //Update the time waiting for these players
        for (Match matchStillWaiting : new ArrayList<Match>(matchingMatches)) {

            for (Player waitingTeam1Player : matchStillWaiting.getTeam1().getPlayers()) {

                waitingTeam1Player.updateTimeWaiting();
            }

            for (Player waitingTeam2Player : matchStillWaiting.getTeam2().getPlayers()) {

                waitingTeam2Player.updateTimeWaiting();
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

            for(Player cancelledPlayerT1: match.getTeam1().getPlayers()) {

                cancelledPlayerT1.setIsMatched(false);
            }

            for(Player cancelledPlayerT2 : match.getTeam2().getPlayers()) {

                cancelledPlayerT2.setIsMatched(false);
            }
            matchingMatches.remove(match);
            cancelledMatches.add(match);
        }

        maxEloDifference += 20;
    }
}