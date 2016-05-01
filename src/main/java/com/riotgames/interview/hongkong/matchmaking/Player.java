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
    private long wins;
    private long losses;
    private double winRatio;

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

    /**
     * Flag to indicate whether a player has been matched into a game or not
     */
    private Boolean isMatched = false;

    /**
     * The amount of time the player has been CURRENTLY waiting for a match. The greater amount of time they have been waiting,
     * the more 'flexibile' the system is in terms of matching them.
     * WARNING: This allows better user experience (not waiting too long for a match), but may decrease how even a match is
     */
    private int timeWaiting = 0;

    /**
     * The amount of time the player has waited for an entire simulation / match making queue. Mainly for statistica purposes.
     * Note: 'time' is measured in 'number of rounds' i.e. where a unit of time = 2, means that the player has been waiting 2 rounds.
     */
    private int totalTimeWaiting = 0;

    /**
     * The amount of games the player has played in this simulator. For statistical purposes
     */
    private int gamesPlayedInSim = 0;

    public Player(String name, long wins, long losses) {

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

    public void setWins(long newWins) {

        this.wins = newWins;
        double totalGames = this.wins + this.losses;
        this.winRatio = this.wins / totalGames;
    }

    public void setLosses(long newLosses) {

        this.losses = newLosses;
        double totalGames = this.wins + this.losses;
        this.winRatio = this.wins / totalGames;
    }

    public void setIsMatched(Boolean isMatched) {

        this.isMatched = isMatched;
    }

    public Boolean getIsMatched() {

        return this.isMatched;
    }

    public int getTimeWaiting() {

        return this.timeWaiting;
    }

    /**
     * Update the time waiting by one (probably because the player didn't get a game in this round)
     */
    public void updateTimeWaiting() {

        this.timeWaiting += 1;
        this.totalTimeWaiting += 1;
    }

    /**
     * Reset the time waiting back to zero. Most likely means the player just got matched and played a game.
     */
    public void resetTimeWaiting() {

        this.timeWaiting = 0;
    }

    public int getTotalTimeWaiting() {

        return this.totalTimeWaiting;
    }

    public void updateGamesPlayedInSim() {

        gamesPlayedInSim += 1;
    }

    public int getGamesPlayedInSim() {

        return this.gamesPlayedInSim;
    }

    public String toString() {

        return "\n" + this.name + " / " + this.wins + "W / " + this.losses + "L / " + this.winRatio + "WR / " + this.eloRating + "ELO";
    }
}
