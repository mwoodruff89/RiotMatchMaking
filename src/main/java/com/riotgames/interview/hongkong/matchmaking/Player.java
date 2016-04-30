package com.riotgames.interview.hongkong.matchmaking;

/**
 * <p>
 * Representation of a player.
 * </p>
 * <p>
 * As indicated in the challenge description, feel free to augment the Player
 * class in any way that you feel will improve your final matchmaking solution.
 * <strong>Do NOT remove the name, wins, or losses fields.</strong> Also note
 * that if you make any of these changes, you are responsible for updating the
 * {@link SampleData} such that it provides a useful data set to exercise your
 * solution.
 * </p>
 */
public class Player {

    private final String name;
    private final long wins;
    private final long losses;
    private final float winRatio;

    /**
     * <p> Elo rating of the player </p>
     * Ref: https://en.wikipedia.org/wiki/Elo_rating_system
     *
     * PLEASE NOTE: In this assignment, the players have already played (wins + losses) amount of games.
     * Elo ratings are generated and incremented / decremented from the the player's match based on who they play.
     *
     * For example, if the player faces a difficult opponent / team and they win, they are rewarded more points.
     * Conversely, if the player faces an opponent / team where they are 'expected' to lose, they will lose more points.
     *
     * However, this assignment does not have any rating data or 'Match History' data. Therefore it's impossible to calculate
     * the Elo rating for this player based on just the wins and losses (who knows if the player won many 'easy' games or
     * many 'hard' games? The rating would be different in each case).
     *
     * Therefore, there are two possible solutions:
     *
     * 1. Provide a 'guess' Elo rating in the Sample Data.
     * 2. Create a 'Dumb' Elo rating and be base the player's initial Elo Rating on the following calculation:
     *
     * <p> PlayerInitialElo = 1000 * WinRatio </p>
     *
     * Where 1000 represents a base Rating (for example a player with 0 games, or 100 wins and 100 losses, should have a rating of 1000
     *
     * <p> In this assignment, I will use solution 2</p>
     *
     * Going forward, the player's Elo Rating will be updated in the proper way whereby more points are awarded /
     * deducted depending on how 'surprising' the result is. Please see the linked Wikipedia article for more information
     * on how I calculate the player's Elo Rating for each match.
     *
     */
    private int eloRating;

    public Player(String name, long wins, long losses) {
        this.name = name;
        this.wins = wins;
        this.losses = losses;
        System.out.print("wins" + wins);
        System.out.print("losses" + wins);
        System.out.print("wins + losses" + (wins + losses));

        if (wins + losses == 0) {

            this.winRatio = (float) 0.5;
        } else {

            float totalGames = wins + losses;
            this.winRatio = wins / totalGames;
        }
    }

    public String getName() {
        return name;
    }

    public long getWins() {
        return wins;
    }

    public long getLosses() {
        return losses;
    }

    public double  getWinRatio() {return winRatio; }

}
