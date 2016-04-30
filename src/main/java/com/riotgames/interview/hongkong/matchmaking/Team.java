package com.riotgames.interview.hongkong.matchmaking;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by michaelwoodruff on 30/4/2016.
 */
public class Team {

    /**
     * Set of players in the team
     */
    private HashSet<Player> players;

    /**
     * The mean / average elo score of players in this team
     */
    private int averageElo = 0;

    /**
     * The sum of all the elo ratings of the players
     */
    private int totalElo = 0;

    /**
     * The mean / average win rating of players in this team;
     */
    private double averageWinRating = 0;

    /**
     * The sum of all the win ratings of the players
     */
    private double totalWinRating = 0;

    /**
     * Constructor which creates an instance of a team based on a set of given players
     */
    public Team(Set<Player> players) {

        this.players = new HashSet<Player>(players);

        if(players.size() > 0) {

            for (Player player : this.players) {

                totalElo += player.getEloRating();
                totalWinRating += player.getWinRatio();
            }

            averageElo = totalElo / this.teamSize();
            averageWinRating += totalWinRating / this.teamSize();
        }
    }

    public Set<Player> getPlayers() { return players; }

    public int teamSize() { return players.size(); }

    public int getAverageElo() { return averageElo; }

    public double getAverageWinRating() { return averageWinRating; }

    public void addPlayer(Player player) {

        players.add(player);
        totalElo += player.getEloRating();
        totalWinRating += player.getWinRatio();

        averageElo = totalElo / teamSize();
        averageWinRating = totalWinRating / teamSize();
    }

    public String toString() {

        return "\n Team Size: " + teamSize() + "\n Average Elo Rating: " + getAverageElo() +
                "\n Average Win Rating: " + getAverageElo();

    }
}
