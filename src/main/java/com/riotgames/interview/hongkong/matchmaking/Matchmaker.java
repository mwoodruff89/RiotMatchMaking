package com.riotgames.interview.hongkong.matchmaking;

import java.util.ArrayList;

public interface Matchmaker {

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
     * @param playersPerTeam - The number of players required in each team
     * @param rule - The matching rule / algorithm to be applied to find matches
     * @return An appropriate match or null if there is no appropriate match
     */
    Match findMatchWithRule(int playersPerTeam, MatchmakerImpl.MatchMakingRule rule);


    /**
     * <p>
     *     Find a set of matches with the given number of players per team
     * </p>
     * @param playersPerTeam The number of players required in each team
     * @return an appropriate list of matches or null if there are no appropriate match
     */
    ArrayList<Match> findMatches(int playersPerTeam);

    /**
     * <p>
     *     Find a set of matches with the given number of players per team the the given matching algorithm
     * </p>
     * @param playersPerTeam - The number of players required in each team
     * @param rule - The matching rule / algorithm to be applied to find matches
     * @return - An appropriate lise of matches of null if there are no appropriate matches
     */
    ArrayList<Match> findMatchesWithRule(int playersPerTeam, MatchmakerImpl.MatchMakingRule rule);

    /**
     * <p>
     * Add a player for matching.
     * </p>
     * */
    void enterMatchmaking(Player player);
}