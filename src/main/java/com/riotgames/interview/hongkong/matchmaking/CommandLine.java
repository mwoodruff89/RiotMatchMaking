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
                System.out.println("java CommandLine <Match-Type> <Matching-Alg> <Sorting-Alg>");
                System.out.println("java CommandLine <Match-Type> <Matching-Alg> <Sorting-Alg> <Team-Size>");
                System.out.println("java CommandLine Simulator");
                System.out.println("Arguments:");
                System.out.println("<Match-Type> = [Single | Multiple]");
                System.out.println("<Matching-Alg> = [Elo | WinRating]");
                System.out.println("<Sorting-Alg> = [NonSorted | Sorted]");
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

                Boolean isSorted = false;
                if(args.length >= 3) {

                    if(args[2].equals("Sorted")) {

                        isSorted = true;
                    } else if(!args[2].equals("NonSorted")) {

                        System.out.println("Invalid Sorting Algorithm");
                    }
                } else {

                    isSorted = true;
                }
                //Optional Match Size
                int matchSize = 5;
                if(args.length >= 4) {

                    matchSize = Integer.parseInt(args[3]);
                }

                MatchmakerImpl matchmaker = new MatchmakerImpl();

                if(args[0].equals("Single")) {

                    Match match = matchmaker.findMatchWithRuleAndIsSorted(matchSize, rule, isSorted);
                    System.out.println(match);
                    System.out.println(match.getGame());
                    match.playMatch();
                    System.out.println(match.getGame());
                } else {

                    int playedMatches = 0;

                    while (playedMatches < 200) {

                        matchmaker.findMatchesWithRuleAndIsSorted(matchSize, rule, isSorted);
                        matchmaker.playMatches();
                        playedMatches = matchmaker.getCompletedMatches().size();
                    }

                    //Calculate stats
                    double totalProbability = 0;
                    for (Match match : matchmaker.getCompletedMatches()) {

                        totalProbability += match.getGame().getWinningProbility();
                    }
                    double averageProbability = totalProbability / matchmaker.getCompletedMatches().size();
                    SampleData.printWRLadder(5);
                    System.out.printf("AVERAGE Match Win/Lose Probability OF ALL GAMES: %s\n", averageProbability);
                }
            }
        }
    }
}
