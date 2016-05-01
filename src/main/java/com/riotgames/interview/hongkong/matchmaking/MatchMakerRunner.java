package com.riotgames.interview.hongkong.matchmaking;

/**
 * MatchMakerRunner creates instances of the MatchmakerImpl class and extracts a / a set of match /matches
 */
public class MatchMakerRunner {

    /**
     * Given a set of parameters (below) will find a single match and print the results / stats
     * @param playersPerTeam - Number of players required in the match
     * @param rule - The Matching algorithm (Elo or Win Ratio)
     * @param isSorted - Whether should keep a sorted list of the players to improve selection performance
     * @param printStats - Flag to indicate if statistics should be printed to the console
     */
    static void runSingleMatchMaker(int playersPerTeam,  MatchmakerImpl.MatchMakingRule rule, Boolean isSorted, Boolean printStats) {

        MatchmakerImpl matchmaker = new MatchmakerImpl();

        Match match = matchmaker.findMatchWithRuleAndIsSorted(playersPerTeam, rule, isSorted);
        if(printStats) {

            System.out.println(match);
            System.out.println(match.getGame());
        }

        matchmaker.playMatch(match);

        if(printStats) {

            System.out.println(match.getGame());
        }
    }

    /**
     * Given a set of parameters (below) will iteratively find a set of matches and then 'play' those mathches' games. Then,
     * follow the games result will update the player's stats and then attempt to match another round of players.
     * @param matchesToRun - Minimum number of matches for the runner to run
     * @param playersPerTeam - Number of players in each team match
     * @param rule - The matching rule / algorithm (Elo / Winning Ratio)
     * @param isSorted - Whether we should keep a sorted list of players to improve player selection performance
     * @param printStats -Flag to indicate if stats should be printed to the console
     */
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