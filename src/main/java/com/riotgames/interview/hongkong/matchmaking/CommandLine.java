package com.riotgames.interview.hongkong.matchmaking;
import com.riotgames.interview.hongkong.matchmaking.MatchmakerImpl;
import com.sun.jdi.connect.Connector;

import java.util.ArrayList;

/**
 * Created by michaelwoodruff on 30/4/2016.
 */
public class CommandLine {

    public static void main(String [] args) {

        if(args.length == 0) {

            System.out.print("Please specify command line arugments. Use java CommandLine Help for more info");
        } else {

            if (args[0].equals("Help")) {

                System.out.println("Commands To Help You: ");
                System.out.println("java CommandLine Help ");
                System.out.println("java CommandLine <Match-Type> <Matching-Alg>");
                System.out.println("java CommandLine <Match-Type> <Matching-Alg> <Team-Size>");
                System.out.println("java CommandLine Simulator");
                System.out.println("Arguments:");
                System.out.println("<Match-Type> = [Single | Multiple]");
                System.out.println("<Matching-Alg> = [Elo | WinRating]");
                System.out.println("<TeamSize> > 0>");
            } else if (args[0].equals("Single") || args[0].equals("Multiple")) {

                MatchmakerImpl.MatchMakingRule rule;
                if(args.length >= 2) {

                    if(args[1].equals("Elo")) {

                        rule = MatchmakerImpl.MatchMakingRule.Elo;
                    } else if (args[1].equals("WinRating")) {

                        rule = MatchmakerImpl.MatchMakingRule.WinRatio;
                    } else {

                        System.out.println("Invalid Matching Algorithm");
                        return;
                    }
                } else {
                    //Default Rule is Elo
                    rule = MatchmakerImpl.MatchMakingRule.Elo;
                }

                //Optional Match Size
                int matchSize = 5;
                if(args.length >= 3) {

                    matchSize = Integer.parseInt(args[2]);
                }

                Matchmaker matchmaker = new MatchmakerImpl();

                if(args[0].equals("Single")) {

                    Match match = matchmaker.findMatchWithRule(matchSize, rule);
                    System.out.println(match);
                    Game game = new Game(match);
                    System.out.println(game);
                    game.playMatch();;
                    System.out.println(game);
                } else {

                    ArrayList<Match> matches = matchmaker.findMatchesWithRule(matchSize, rule);
                    System.out.printf("TOTAL MATCHES: %s\n", matches.size());
                    double totalProbability = 0;
                    double totalPlayers = 0;
                    for (Match match : matches) {

                        System.out.println(match);
                        Game game = new Game(match);

                        totalProbability += game.getWinningProbility();
                        totalPlayers += (match.getTeam1().teamSize()) + (match.getTeam2().teamSize());
                        System.out.println(game);
                        game.playMatch();
                        System.out.println(game);
                    }

                    totalProbability = totalProbability / matches.size();
                    totalPlayers = totalPlayers / SampleData.getPlayers().size();
                    System.out.printf("AVERAGE Match Win/Lose Probability OF ALL GAMES: %s\n", totalProbability);
                    System.out.printf("%s of all players matched", totalPlayers);
                }
            }
        }
    }
}