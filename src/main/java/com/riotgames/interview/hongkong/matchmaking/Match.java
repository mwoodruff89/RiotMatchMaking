package com.riotgames.interview.hongkong.matchmaking;

import java.util.HashSet;
import java.util.Set;
public class Match {

    private Team team1;
    private Team team2;

    private double kMaxDifferenceElo = 20;
    private double maxDifferencWinRatio = 0.1;

    /**
     * The maximum size for each team in this match. i.e. if it's 5 then it's a 5v5 match etc.
     */
    private int maxTeamSize = 0;

    /**
     * Customizable maximum difference of the win rating between two players. We set the default as Elo.
     */
    public double maxDifference = kMaxDifferenceElo;

    /**
     * The average win rating of the players
     */
    public double averageRating = 0;

    /**
     * Flag instance variable indicating when the maximum amount of players have been added to each team
     * and the average win rating of each team is within the maximum difference.
     */
    public boolean isFullyMatched = false;

    /**
     * The Matchmaker in charge of adding players and teams to this match
     */
    private MatchmakerImpl matchmaker;

    /**
     * The game that is played between the two teams. Is null until the match is fully matched.
     */
    private Game game = null;

    public MatchmakerImpl getMatchmaker() { return matchmaker; }

    public Team getTeam1() { return team1; }

    public Team getTeam2() {
        return team2;
    }

    public Game getGame() { return game; }

    public void setkMaxDifferenceElo(double newDifference) {

        this.kMaxDifferenceElo = newDifference;
    }

    public Match(HashSet<Player> team1, HashSet<Player> team2, int maxSize, MatchmakerImpl matchMaker) {

        this.team1 = new Team(team1);
        this.team2 = new Team(team2);
        this.maxTeamSize = maxSize;

        this.matchmaker = matchMaker;

        if(this.matchmaker.matchingRule == MatchmakerImpl.MatchMakingRule.WinRatio) {

            maxDifference = maxDifferencWinRatio;

        }

        updateMeanRating();
    }

    /**
     *
     * @param player - The Player who may be added
     * @return - True if the player can be added, otherwise false
     */
    public boolean canAddPlayer(Player player) {

        boolean canAdd = false;

        if(team1.teamSize() == 0 && team2.teamSize() == 0) {

            //Both empty so basically starting a new match
            canAdd = true;
        } else {

            //Match already has players so can only add if the rating falls within the range
            if(matchmaker.matchingRule == MatchmakerImpl.MatchMakingRule.WinRatio) {

                //If the player has been waiting some time, increase the max Difference by 0.05 * timeWaiting
                //i.e. TW = 0, offset =0.1; TW = 1, offset = 0.15; TW = 2, offset = 0.2; TW = 3, offset = 0.25 etc
                double offset = player.getTimeWaiting() > 0 ? maxDifference + (0.05 * player.getTimeWaiting()) : 0;

                if(Math.abs(averageRating - player.getWinRatio()) <= maxDifference + offset) {

                    canAdd = true;
                }
            } else {

                //For every round the player has been waiting, increase the max difference by 20.
                //ie.e TW = 0, offset = 20; TW = 1, offset = 25; TW = 2, offset = 30; TW = 3, offset = 35 etc
                double offset = player.getTimeWaiting() > 0 ? maxDifference + (20 * player.getTimeWaiting()) : 0;

                if(Math.abs(averageRating - player.getEloRating()) <= maxDifference + offset) {

                    canAdd = true;
                }
            }
        }

        return canAdd;
    }

    /**
     * Adds a player to the Match. Assumes that the player can be added to the match
     * (caller calls canAddWithRating first).
     * @param player - The player to be added to the Match
     */
    public void addPlayer(Player player) {

        if(team1.teamSize() < maxTeamSize) {

            team1.addPlayer(player);
        } else if (team2.teamSize() < maxTeamSize){

            team2.addPlayer(player);
        }

        if(team1.teamSize() == maxTeamSize && team2.teamSize() == maxTeamSize) {

            isFullyMatched = true;
            game = new Game(this);
        }

        updateMeanRating();
    }

    public void playMatch() {

        game.playMatch();
    }
    /**
     * Helper method which updates the mean / average rating of this match. Should be called after a player is added
     * to the match.
     */
    private void updateMeanRating() {

        float averageRatingTeam1 = 0;
        for (Player player : team1.getPlayers()) {

            if(matchmaker.matchingRule == MatchmakerImpl.MatchMakingRule.WinRatio) {

                averageRatingTeam1 += player.getWinRatio();
            } else {
                averageRatingTeam1 += team1.getAverageElo();
            }
        }

        float averageRatingTeam2 = 0;
        for (Player player : team2.getPlayers()) {

            if(matchmaker.matchingRule == MatchmakerImpl.MatchMakingRule.WinRatio) {

                averageRatingTeam2 += player.getWinRatio();
            } else {

                averageRatingTeam2 += player.getEloRating();
            }
        }

        float totalAverage = averageRatingTeam1 + averageRatingTeam2;
        totalAverage = totalAverage / (team1.teamSize() + team2.teamSize());
        this.averageRating = totalAverage;
    }

    public String toString() {

        return "*-- Match ---*\nTeam Size: " + maxTeamSize + "\nAverage Rating: " + averageRating +
                "\nTeam 1: " + team1 + "\nTeam 2: " + team2;
    }
}
