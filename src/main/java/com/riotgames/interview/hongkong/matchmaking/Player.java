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
