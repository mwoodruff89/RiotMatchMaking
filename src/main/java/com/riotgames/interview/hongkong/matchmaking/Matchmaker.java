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
     * <p>
     *     Find a set of matches with the given number of players per team
     * </p>
     * @param playersPerTeam The number of players required in each team
     * @return an appropriate match or null if there is no appropriate match
     */
    ArrayList<Match> findMatches(int playersPerTeam);

    /**
     * <p>
     * Add a player for matching.
     * </p>
     */
    void enterMatchmaking(Player player);

    /**
     * Set any relevant editable rules for tha match making
     */
    void setRules(MatchmakerImpl.MatchMakingRule rule);
}