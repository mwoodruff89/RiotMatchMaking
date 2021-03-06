package com.riotgames.interview.hongkong.matchmaking;
import java.util.Random;

/**
 * The Game class represents a Game for a match of two teams of players. A game can be played and a winning and losing team
 * will be selected based on the winning probability of each team.
 *
 * Additionally, following a game being played, the Game class will update the players on each team by assigning them updated
 * wins / losses and new Elo ratings.
 */
public class Game {

    /**
     * The match (two teams and a set of players) for the game. Used to calculate win percentage for the Game for each
     * team and for the playMatch method
     */
    private Match match;

    /**
     * The winning probability of Team 1
     */
    private double winningProbility = 0;

    /**
     * The team that wins the match. Is null before a match is played
     */
    private Team winningTeam = null;

    /**
     * The team that lost the match. Is null before a match is player.
     */
    private Team losingTeam = null;

    /**
     * The K factor used for how much a win / loss will affect a player's rating. The higher the K value, the more
     * unstable the user's rating will be. The values below have been deduced following the Wikipedia article on Elo.
     */

    /**
     * KFactor for players wh have played less than 30 games
     */
    private final int kFactorHigh = 30;

    /**
     * KFactor for players under 2400 rating
     */
    private final int kFactorMid = 15;

    /**
     * KFactor for players above 2400 and have atleast 30 games.
     */
    private final int kFactorLow = 10;

    /**
     * Constructor for instance of a Game.
     * @param match A match with two teams each with a set of players.
     */
    public Game(Match match) {

        this.match = match;
        setupProbability();;
    }

    //*---Getters---*\\\
    public double getWinningProbility() { return winningProbility; }

    /**
     * Init helper method. Calculates the win probability of team 1 winning (and inversely team 2 winning)
     *
     * This calculation is based from https://metinmediamath.wordpress.com/2013/11/12/sports-elo-rating-and-win-probability-carlsen-vs-anand/
     *
     * @return The winning probability of Team 1
     */
    private void setupProbability() {

        if(match.getMatchmaker().matchingRule == MatchmakerImpl.MatchMakingRule.Elo) {

            double difference = Math.abs(match.getTeam1().getAverageElo() - match.getTeam2().getAverageElo());
            double probability = 0;
            if (difference > 0) {
                //Team 2 more likely to win so deduct from 1. Don't minus from 1
                probability = 1 / (1 + Math.exp(0.00583 * difference - 0.0505));

            } else {
                //Team 1 more likely to win
                probability = 1 - 1 / (1 + Math.exp(0.00583 * difference - 0.0505));
            }

            this.winningProbility = probability;
        } else {

            double sumOfWinRating = match.getTeam1().getAverageWinRating() + match.getTeam2().getAverageWinRating();
            this.winningProbility =  match.getTeam1().getAverageWinRating()/ sumOfWinRating;
        }
    }

    /**
     * Randomly assigns a winner of the match based on the winning percentage for each team.
     * It will also assign new win / loss values to each player object and update their Elo rating.
     */
    public void playMatch() {

        generateWinner();

        if(match.getMatchmaker().matchingRule == MatchmakerImpl.MatchMakingRule.Elo) {

            double transformedRatingWinner = Math.pow(10, winningTeam.getAverageElo() / 400);
            double transformedRatingLoser = Math.pow(10, losingTeam.getAverageElo() / 400);

            double expectedScoreWinner = transformedRatingWinner / (transformedRatingWinner + transformedRatingLoser);
            double expectedScoreLoser = transformedRatingLoser / (transformedRatingWinner + transformedRatingLoser);

            //Update winning player scores
            for (Player winningPlayer : winningTeam.getPlayers()) {

                int newElo = (int) (winningPlayer.getEloRating() + kFactorForPlayer(winningPlayer) * (1 - expectedScoreWinner));
                winningPlayer.setEloRating(newElo);
            }

            //Update loosing player scores
            for (Player losingPlayer : losingTeam.getPlayers()) {

                int newElo = (int) (losingPlayer.getEloRating() + kFactorForPlayer(losingPlayer) * (0 - expectedScoreLoser));
                losingPlayer.setEloRating(newElo);
            }
        } else {

            for (Player winningPlayer : winningTeam.getPlayers()) {

                winningPlayer.setWins(winningPlayer.getWins() + 1);
            }

            for(Player losingPlayer : losingTeam.getPlayers()) {

                losingPlayer.setLosses(losingPlayer.getLosses() + 1);
            }
        }
    }

    /**
     * Given a player, will determine the appropriate kFactor to use for him
     * @param player - The given player with a Elo Rating and total matches which is used to deduce the k factor
     * @return An integer represent the kFactor to be used
     */
    private int kFactorForPlayer(Player player) {

        int kFactor = 0;

        if(player.getEloRating() > 2400 && player.getWins() + player.getLosses() > 30) {

            kFactor = kFactorHigh;
        } else if(player.getEloRating() > 2400 && player.getWins() + player.getLosses() < 30) {

            kFactor = kFactorLow;
        } else if(player.getEloRating() < 2400) {

            kFactor = kFactorMid;
        }

        return kFactor;
    }

    /**
     * Helper Method. Uses basic Java's basic random math function to get a random double between 0->1.
     * This number is then used to deduce the winner based on each teams winning probabilities
     */
    private void generateWinner() {

        Random generator = new Random();
        double num = generator.nextDouble();

        if(num < winningProbility) {

            winningTeam = match.getTeam1();
            losingTeam = match.getTeam2();
        } else {

            winningTeam = match.getTeam2();
            losingTeam = match.getTeam1();
        }
    }

    //*---To String---*\\
    public String toString() {

        if(losingTeam != null && winningTeam != null) {

            if(winningTeam == match.getTeam1()) {
                return "Team 1 Won!";
            } else if (winningTeam == match.getTeam2()) {
                return "Team 2 Won!";
            }
            return "Who won?";
        } else {

            return "\nProbabilty of Team 1 Winning: " + winningProbility + "\nProbability of Team 2 Winning: " + (1 - winningProbility);
        }
    }
}
