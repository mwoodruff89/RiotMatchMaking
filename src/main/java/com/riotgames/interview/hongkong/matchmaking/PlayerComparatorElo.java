package com.riotgames.interview.hongkong.matchmaking;

import java.util.Comparator;

/**
 * Comparator class to help sort Players based on Elo Rating
 */
public class PlayerComparatorElo implements Comparator<Player> {

    public int compare(Player a, Player b) {

        if (a.getEloRating() > b.getEloRating()) {

            return -1;
        } else if (a.getEloRating() < b.getEloRating()) {

            return 1;
        } else {
            return 0;
        }
    }
}
