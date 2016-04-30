package com.riotgames.interview.hongkong.matchmaking;

import java.util.HashSet;
import java.util.Set;
public class Match {

    private HashSet<Player> team1;
    private HashSet<Player> team2;

    private double kMaxDifferenceElo = 20;
    private double kMaxDifferencWinRatio = 0.1;
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
     * Match making rule to be utilised for matching players for this match. Default is Elo please see the enum for
     * different rules.
     */
    public MatchmakerImpl.MatchMakingRule matchMakingRule = MatchmakerImpl.MatchMakingRule.Elo;

    public Match(HashSet<Player> team1, HashSet<Player> team2, int maxSize) {

        this.team1 = team1;
        this.team2 = team2;
        this.maxTeamSize = maxSize;

        updateMeanRating();
    }

    public Set<Player> getTeam1() {
        return team1;
    }

    public Set<Player> getTeam2() {
        return team2;
    }

    /**
     * Custom setter for the match making rule which will also update the maximum difference when the rule is changed
     * @param newRule - The new rule to use for adding players to this match.
     */
    public void setMatchMakingRule(MatchmakerImpl.MatchMakingRule newRule) {

        this.matchMakingRule = newRule;

        if(this.matchMakingRule == MatchmakerImpl.MatchMakingRule.WinRatio) {

            maxDifference = kMaxDifferencWinRatio;
        } else {

            maxDifference = kMaxDifferenceElo;
        }
    }

    /**
     *
     * @param rating - The rating of the player assumed to be added
     * @return - True if the player can be added, otherwise false
     */
    public boolean canAddWithRating(double rating) {

        boolean canAdd = false;

        if(team1.isEmpty() && team2.isEmpty()) {

            //Both empty so basically starting a new match
            canAdd = true;
        } else {

            //Match already has players so can only add if the rating falls within the range
            if(matchMakingRule == MatchmakerImpl.MatchMakingRule.WinRatio) {

                if(Math.abs(averageRating - rating) <= maxDifference) {

                    canAdd = true;
                }
            } else {

                if(Math.abs(averageRating - rating) <= maxDifference) {

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

        if(team1.size() < maxTeamSize) {

            team1.add(player);
        } else if (team2.size() < maxTeamSize){

            team2.add(player);
        }

        if(team1.size() == maxTeamSize && team2.size() == maxTeamSize) {

            isFullyMatched = true;
        }

        updateMeanRating();
    }

    /**
     * Helper method which updates the mean / average rating of this match. Should be called after a player is added
     * to the match.
     */
    private void updateMeanRating() {

        float totalRatingTeam1 = 0;
        for (Player player : new HashSet<Player>(this.team1)) {

            if(matchMakingRule == MatchmakerImpl.MatchMakingRule.WinRatio) {

                totalRatingTeam1 += player.getWinRatio();
            } else {

                totalRatingTeam1 += player.getEloRating();
            }
        }

        float totalRatingTeam2 = 0;
        for (Player player : new HashSet<Player>(this.team2)) {

            if(matchMakingRule == MatchmakerImpl.MatchMakingRule.WinRatio) {

                totalRatingTeam2 += player.getWinRatio();
            } else {

                totalRatingTeam2 += player.getEloRating();
            }
        }

        this.averageRating = (totalRatingTeam1 + totalRatingTeam2) / (this.team1.size() + this.team2.size());
    }
}
