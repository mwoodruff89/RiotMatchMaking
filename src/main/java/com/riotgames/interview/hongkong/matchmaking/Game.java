package com.riotgames.interview.hongkong.matchmaking;

/**
 * Created by michaelwoodruff on 30/4/2016.
 */
public class Game {

    /**
     * The match (two teams and a set of players) for the game. Used to calculate win percentage for the Game for each
     * team and for the playMatch method
     */
    private Match match;

    /**
     * Constructor for instance of a Game.
     * @param match A match with two teams each with a set of players.
     */
    public Game(Match match) {

        this.match = match;
    }

    public Match getMatch() { return match; }

    /**
     * Randomly assigns a winner of the match based on the winning percentage for each team.
     */
    public void playMatch() {

    }

}
