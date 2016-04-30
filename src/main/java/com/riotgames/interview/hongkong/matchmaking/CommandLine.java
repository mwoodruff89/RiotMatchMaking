package com.riotgames.interview.hongkong.matchmaking;
import com.riotgames.interview.hongkong.matchmaking.MatchmakerImpl;
/**
 * Created by michaelwoodruff on 30/4/2016.
 */
public class CommandLine {

    public static void main(String [] args) {

        if(args.length == 0) {

            System.out.print("Please specify command line arugments. java CommandLine Help for more info");
        } else {

            if (args[0].equals("Help")) {

                System.out.println("Commands To Help You: ");
                System.out.println("java CommandLine Help");
                System.out.println("java CommandLine Elo");
                System.out.println("java CommandLine WinRatio");
            } else if (args[0].equals("Elo")) {

                Matchmaker matchmaker = new MatchmakerImpl();
                Match match= matchmaker.findMatch(5);
                System.out.println(match);

                Game game = new Game(match);
                System.out.println(game);
                game.playMatch();;
                System.out.println(game);

            } else if (args[1].equals("WinRatio")) {


            }
        }
    }
}
