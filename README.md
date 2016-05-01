# RiotMatchMaking
Assignment by Michael Woodruff for Riot Games

BUILD INSTRUCTIONS

To Build and Run
1. From the terminal / command line cd into the /target/classes/ directory
2. Run the following command: java com.riotgames.interview.hongkong.matchmaking.CommandLine

You should get the response:

Please specify command line arugments. Use java CommandLine Help for more info

As the response says, now run:

java com.riotgames.interview.hongkong.matchmaking.CommandLine Help

and you will get the command line argument specifications

Example commands:

Mutli Match making
java com.riotgames.interview.hongkong.matchmaking.CommandLine 200 Elo Sorted 5
java com.riotgames.interview.hongkong.matchmaking.CommandLine 100 WR Sorted 2

Single Match making
java com.riotgames.interview.hongkong.matchmaking.CommandLine Elo NonSorted 5
java com.riotgames.interview.hongkong.matchmaking.CommandLine WR Sorted 3

If you have IntelliJ IDE you may also run the build from there and use one of the two build configurations:

- AllTests - Runs unit tests
- CommandLine - Runs the command line target with the default build configuration arguments (which you may change)

DESCRIPTION

You may launch the java program using the command line arguments above. Here is a quick description of what each argument does

Given an OPTIONAL integer x (first argument) a multi match making command will attempt to match x matches using the match making algorithm you specified (second argument). Note that if you do not specify the first argument, then the program will only make one match and then return. 

For the second argument, you may choose between the Elo matching algorithm, which has been adapted into this Java program from Wikipedia. Otherwise, you may use the Win Ratio algorithm, which is a basic algorithm created by myself which matches players based on their win ratios.


Furthermore, you may choose to specify two more arguments; a sorting rule [Sorted | NonSorted] which will keep  (or not) the players from the Sample Data class sorted when choosing them for matching. The other (and final) argument is another integer which specifies the team size. I.e. if you input '5' as the final argument you will initiate the matching program for 5v5 matchups, 4 for 4v4 etc.
