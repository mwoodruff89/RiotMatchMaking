package com.riotgames.interview.hongkong.matchmaking;
import java.util.Comparator;

/**
 * Comparator class to help sort Players based on Average Win Rating
 */
public class PlayerComparatorWinRatio implements Comparator<Player> {

    public int compare(Player a, Player b) {

        if (a.getWinRatio() > b.getWinRatio()) {

            return -1;
        } else if (a.getWinRatio() < b.getWinRatio()) {

            return 1;
        } else {
            return 0;
        }
    }

}