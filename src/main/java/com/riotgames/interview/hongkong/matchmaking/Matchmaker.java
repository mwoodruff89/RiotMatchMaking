package com.riotgames.interview.hongkong.matchmaking;

import java.util.ArrayList;

public interface Matchmaker {

    ArrayList<Match> fullyMatchedMatches = new ArrayList<Match>();

    ArrayList<Match> completedMatches = new ArrayList<Match>();

    /**
     * <p>
     * Find a match with the given number of players per team.
     * </p>
     *
     * @param playersPerTeam the number of players required in each team
     * @return an appropriate match or null if there is no appropriate match
     */
    Match findMatch(int playersPerTeam);

    /**
     * Find a match with the given number of players per team and the given matching algorithm
     *
     * @param playersPerTeam - The number of players required in each team
     * @param rule           - The matching rule / algorithm to be applied to find matches
     * @return An appropriate match or null if there is no appropriate match
     */
    Match findMatchWithRule(int playersPerTeam, MatchmakerImpl.MatchMakingRule rule);

    /**
     * Find a match with the given number of players per team, a given matching algorithm and whether the Player List
     * Should be sorted before entering them to the match making system
     *
     * @param playersPerTeam - The number of players required in each team
     * @param rule           - The matching rule / algorithm to be applied to find matches
     * @param isSorted       - Whether the match maker will sort the player array list first before extracting players
     * @return - The match which was found
     */
    Match findMatchWithRuleAndIsSorted(int playersPerTeam, MatchmakerImpl.MatchMakingRule rule, Boolean isSorted);

    /**
     * <p>
     * Find a set of matches with the given number of players per team
     * </p>
     *
     * @param playersPerTeam The number of players required in each team
     * @return an appropriate list of matches or null if there are no appropriate match
     */
    ArrayList<Match> findMatches(int playersPerTeam);

    /**
     * <p>
     * Find a set of matches with the given number of players per team and the given matching algorithm
     * </p>
     *
     * @param playersPerTeam - The number of players required in each team
     * @param rule           - The matching rule / algorithm to be applied to find matches
     * @return - An appropriate lise of matches of null if there are no appropriate matches
     */
    ArrayList<Match> findMatchesWithRule(int playersPerTeam, MatchmakerImpl.MatchMakingRule rule);

    /**
     * <p>
     * Find a set of matches with the given number of players per team, a given matching algorithm adnd whether the
     * player list should be sorted before entering them to the match making system.
     * </p>
     *
     * @param playersPerTeam The number of players required in each team
     * @param rule           - The matching rule / algorithm to be applied to find matches
     * @param isSorted       - Whether the match maker will sort the player array list first before extracting players
     * @return - The list of matches found
     */
    ArrayList<Match> findMatchesWithRuleAndIsSorted(int playersPerTeam, MatchmakerImpl.MatchMakingRule rule, Boolean isSorted);

    /**
     * <p>
     *     Add a player for matching
     * </p>
     * @param player - The player to be added to the match making service
     */
    void enterMatchmaking(Player player);

    /**
     * The match maker will process and play the game of the given match. Then it will clean up the match from the matched list and
     * move it to the completed list. Then, the match maker will release all players so they can be matched to another game.
     * @param match - The match to be played
     */
    void playMatch(Match match);

    /**
     * The match maker processes (play the games of) all the currently completed match ups. Then it will clean up all
     * the matches from the matched list and move them to the completed list. Then, the match maker will release all players
     * so they can be matched to another game
     */
    void playMatches();
}