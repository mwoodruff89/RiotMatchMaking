package com.riotgames.interview.hongkong.matchmaking;
import java.util.Comparator;
/**
 * Created by michaelwoodruff on 30/4/2016.
 */
public class PlayerComparatorWinRatio implements Comparator<Player> {

    public int compare(Player a, Player b) {
        if (a.getWinRatio() > b.getWinRatio()) {

            return 1;
        } else if (a.getWinRatio() < b.getWinRatio()) {

            return -1;
        } else {
            return 0;
        }
    }

}