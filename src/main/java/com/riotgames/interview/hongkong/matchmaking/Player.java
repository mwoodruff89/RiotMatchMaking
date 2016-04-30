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
public class Player implements Comparable<Player>{

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
     * <p> PlayerInitialElo = 1000 * (WinRatio + AverageWinRatio) </p>
     *
     * Where 1000 represents a base Rating (for example a player with 0 games, should have a rating of 1000
     * And AverageRating represents a offset. This is so the formulae will return 1000 for 50/50 win ratios
     * and return Elo > 1000 for players with more wins than losses and Elo < 1000 for players with less wins than losses
     *
     * Examples:
     * Super Good Player: 1000 * (0.95 + 0.5) = 1450
     * Good Player: 1000 * (0.7 + 0.5) = 1,200
     * Bad Player: 1000 * (0.3 + 0.5) = 800
     * Super Bad Player: 1000 * (0.05 + 0.5) = 550
     * New Player: 1000
     * <p> In this assignment, I will use solution 2</p>
     *
     * Going forward, the player's Elo Rating will be updated in the proper way whereby more points are awarded /
     * deducted depending on how 'surprising' the result is. Please see the linked Wikipedia article for more information
     * on how I calculate the player's Elo Rating for each match.
     *
     */
    private int eloRating = 0;

    /**
     * The base Elo Rating for new players.
     */
    private int kBaseElo = 1000;

    public Player(String name, long wins, long losses) {

        if(name.equals("Ann Owen")) {
            System.out.print("Its ann!");
        }


        //Solve any data corruptions
        wins = wins < 0 ? 0 : wins;
        losses = losses < 0 ? 0 : losses;

        if (wins + losses == 0) {

            //New Player
            this.wins = 0;
            this.losses = 0;
            this.winRatio = (float) 0.5;
            this.eloRating = kBaseElo;
        } else {

            float totalGames = wins + losses;

            // Check for overflow (some of the data from SampleData causes overflow here!)
            // Overflow occured. My solution will be to normalise the data (by dividing to make it a 3 digit number)
            // as logically it would be impossible for a player to get Long.Max wins or losses in reality.
            if(totalGames < 0) {

                this.wins = 0;
                this.losses = 0;
                this.winRatio = (float) 0.5;
                this.eloRating = kBaseElo;
            } else {

                this.wins = wins;
                this.losses = losses;
                this.winRatio = this.wins / totalGames;
            }
        }

        this.name = name;
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

    public double getWinRatio() {return winRatio; }

    public double getEloRating() {

        if(eloRating == 0) {

            double provElo = Math.floor(kBaseElo * (this.winRatio + SampleData.averageWinRating()));
            this.eloRating = (int) provElo;
        }

        return this.eloRating;
    }

    public void setEloRating(int newElo) {

        this.eloRating = newElo;
    }


    public int compareTo(Player player) {

        if(winRatio > player.getWinRatio()) {

            return 1;
        } else if(winRatio < player.getWinRatio()) {

            return -1;
        } else {
            return 0;
        }
    }
}
