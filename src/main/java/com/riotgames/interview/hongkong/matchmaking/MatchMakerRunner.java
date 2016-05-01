package com.riotgames.interview.hongkong.matchmaking;

/**
 * Created by michaelwoodruff on 1/5/2016.
 */
public class MatchMakerRunner {

    static void runSingleMatchMaker(int playersPerTeam,  MatchmakerImpl.MatchMakingRule rule, Boolean isSorted, Boolean printStats) {

        MatchmakerImpl matchmaker = new MatchmakerImpl();

        Match match = matchmaker.findMatchWithRuleAndIsSorted(playersPerTeam, rule, isSorted);
        if(printStats) {

            System.out.println(match);
            System.out.println(match.getGame());
        }
        match.playMatch();

        if(printStats) {

            System.out.println(match.getGame());
        }
    }

    static void runMatchMaker(int matchesToRun, int playersPerTeam, MatchmakerImpl.MatchMakingRule rule, Boolean isSorted, Boolean printStats) {

        MatchmakerImpl matchmaker = new MatchmakerImpl();

        int playedMatches = 0;

        while (playedMatches < matchesToRun) {

            matchmaker.findMatchesWithRuleAndIsSorted(playersPerTeam, rule, isSorted);
            matchmaker.playMatches();
            playedMatches = matchmaker.getCompletedMatches().size();
            if(printStats) {

                System.out.printf("Played matches: %s\n", playedMatches);
            }
        }

        if(printStats) {

            //Calculate stats
            if(matchmaker.matchingRule == MatchmakerImpl.MatchMakingRule.Elo) {

                SampleData.printEloLadder(5);
            } else {

                SampleData.printWRLadder(5);
            }

            double totalProbability = 0;
            for (Match match : matchmaker.getCompletedMatches()) {

                totalProbability += match.getGame().getWinningProbility();
            }
            double averageProbability = totalProbability / matchmaker.getCompletedMatches().size();
            System.out.printf("\nAVERAGE Match Win/Lose Probability OF ALL GAMES: %s\n", averageProbability);

            //Calculate average waiting times
            double totalWaitingTime = 0;
            double totalGamesInSim = 0;
            if(matchmaker.matchingRule == MatchmakerImpl.MatchMakingRule.Elo) {

                for (Player player : SampleData.getPlayersSortedByElo()) {

                    totalWaitingTime += player.getTotalTimeWaiting();
                    totalGamesInSim += player.getGamesPlayedInSim();
                }
            } else {

                for (Player player : SampleData.getPlayersSortedByWinRatio()) {

                    totalWaitingTime += player.getTotalTimeWaiting();
                    totalGamesInSim += player.getGamesPlayedInSim();
                }
            }

            double averageWaitingTime = totalWaitingTime / SampleData.getPlayers().size();
            System.out.printf("Average Waiting Time: %s", averageWaitingTime);
            double averageGamesInSim = totalGamesInSim / SampleData.getPlayers().size();
            System.out.printf("\nAverage Games in Sim: %s", averageGamesInSim);
            System.out.println("\nMax Elo Difference (initial is 20): " + matchmaker.getMaxEloDifference());
        }
    }
}