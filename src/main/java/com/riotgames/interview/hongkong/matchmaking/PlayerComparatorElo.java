package com.riotgames.interview.hongkong.matchmaking;

import java.util.Comparator;

/**
 * Created by michaelwoodruff on 30/4/2016.
 */
public class PlayerComparatorElo implements Comparator<Player> {

    public int compare(Player a, Player b) {
        if (a.getEloRating() > b.getEloRating()) {

            return 1;
        } else if (a.getEloRating() < b.getEloRating()) {

            return -1;
        } else {
            return 0;
        }
    }
}
