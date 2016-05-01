package com.riotgames.interview.hongkong.matchmaking;
import com.riotgames.interview.hongkong.matchmaking.MatchmakerImpl;
import com.sun.jdi.connect.Connector;

import java.util.ArrayList;

/**
 * CommandLine class to handle input from user and to view stats
 */
public class CommandLine {

    static final int kSingleMatchupArgCount = 3;
    static final int kMultiMatchupArgCount = 4;

    /**
     * Main method which initiates the program from command line / terminal
     * @param args - The list of arguments input by the user at the command line
     */
    public static void main(String [] args) {

        if(args.length == 0) {

            System.out.print("Please specify command line arugments. Use java CommandLine Help for more info");
        } else {

            if (args[0].equals("Help")) {

                System.out.println("Commands To Help You: ");
                System.out.println("java CommandLine Help ");
                System.out.println("java CommandLine <Matching-Alg> <Sorting-Alg> <Team-Size> - Single Matchup");
                System.out.println("java CommandLine <Number-of-Matches> <Matching-Alg> <Sorting-Rule> <Team-Size> - Multiple Matchup Simulation");
                System.out.println("java CommandLine Simulator");
                System.out.println("Arguments:");
                System.out.println("<Number-of-Matches> = [ >= 1]");
                System.out.println("<Match-Type> = [Single | Multiple]");
                System.out.println("<Matching-Alg> = [Elo | WR]");
                System.out.println("<Sorting-Rule> = [NonSorted | Sorted]");
                System.out.println("<Team-Size> = [ >=1 ]");
            } else {

                if (args.length == kSingleMatchupArgCount) {
                    //Single Matchup
                    MatchmakerImpl.MatchMakingRule rule = ruleWithArg(args[0]);
                    if(rule == null) {

                        return;
                    }

                    Boolean isSorted = isSortedWithArg(args[1]);
                    if(isSorted == null) {

                        return;
                    }

                    int numberOfPlayers = Integer.parseInt(args[2]);

                    MatchMakerRunner.runSingleMatchMaker(numberOfPlayers, rule, isSorted, true);

                } else if (args.length == kMultiMatchupArgCount) {
                    //Multiple Matchup
                    int numberOfMatches = Integer.parseInt(args[0]);

                    MatchmakerImpl.MatchMakingRule rule = ruleWithArg(args[1]);
                    if(rule == null) {

                        return;
                    }

                    Boolean isSorted = isSortedWithArg(args[2]);
                    if(isSorted == null) {

                        return;
                    }

                    int numberOfPlayers = Integer.parseInt(args[3]);

                    MatchMakerRunner.runMatchMaker(numberOfMatches, numberOfPlayers, rule, isSorted, true);


                } else {

                    System.out.println("Invalid command. Please use help to view command list");
                }
            }
        }
    }

    /**
     * Convenience method to translate the rule argument from command line
     * @param arg - the rule argument from the command line
     * @return - MatchMakerImpl.MatchMakingRule representing if the user wants to use Elo or WR algorithm for matchmaking.
     * Will return null if we can't extract a value
     */
    private static MatchmakerImpl.MatchMakingRule ruleWithArg(String arg) {

        MatchmakerImpl.MatchMakingRule rule = null;

        if (arg.equals("Elo")) {

            rule = MatchmakerImpl.MatchMakingRule.Elo;
        } else if (arg.equals("WR")) {

            rule = MatchmakerImpl.MatchMakingRule.WinRatio;
        } else {

            System.out.println("Invalid Matching Algorithm. Please try again and use 'Elo' or 'WR' as the first argument");
        }

        return rule;
    }

    /**
     * Convenience method to translate the isSorted argument from command line
     * @param arg - the isSorted argument from the command line
     * @return - Boolean for if the user wants the data to be sorted. Will return null if we can't extract a value
     */
    private static Boolean isSortedWithArg(String arg) {

        Boolean isSorted = null;
        if (arg.equals("NonSorted")) {

            isSorted = false;
        } else if (arg.equals("Sorted")) {

            isSorted = true;
        } else {

            System.out.println("Invalid sorting rule. Please try again and enter 'NonSorted' or 'Sorted' as the second argument");
        }

        return isSorted;
    }
}