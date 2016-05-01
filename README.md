# RiotMatchMaking
Assignment by Michael Woodruff for Riot Games

##BUILD INSTRUCTIONS

###To Build and Run
1. From the terminal / command line cd into the /target/classes/ directory
2. Run the following command: java com.riotgames.interview.hongkong.matchmaking.CommandLine

You should get the response:

```
Please specify command line arugments. Use java CommandLine Help for more info
```

As the response says, now run:

```
java com.riotgames.interview.hongkong.matchmaking.CommandLine Help
```

and you will get the command line argument specifications

###Example commands:

####Mutli Match making
```
java com.riotgames.interview.hongkong.matchmaking.CommandLine 200 Elo Sorted 5
```
```
java com.riotgames.interview.hongkong.matchmaking.CommandLine 100 WR Sorted 2
```

####Single Match making
```
java com.riotgames.interview.hongkong.matchmaking.CommandLine Elo NonSorted 5
```
```
java com.riotgames.interview.hongkong.matchmaking.CommandLine WR Sorted 3
```

If you have IntelliJ IDE you may also run the build from there and use one of the two build configurations:

- AllTests - Runs unit tests
- CommandLine - Runs the command line target with the default build configuration arguments (which you may change)

##DESCRIPTION

You may launch the java program using the command line arguments above. Here is a quick description of what each argument does:

Given an OPTIONAL integer x (first argument) a multi match making command will attempt to match x matches using the match making algorithm you specified (second argument). Note that if you do not specify the first argument, then the program will only make one match and then return. 

For the second argument, you may choose between the Elo matching algorithm, which has been adapted into this Java program from Wikipedia. Otherwise, you may use the Win Ratio algorithm, which is a basic algorithm created by myself which matches players based on their win ratios.


Furthermore, you may choose to specify two more arguments; a sorting rule [Sorted | NonSorted] which will keep  (or not) the players from the Sample Data class sorted when choosing them for matching. The other (and final) argument is another integer which specifies the team size. I.e. if you input '5' as the final argument you will initiate the matching program for 5v5 matchups, 4 for 4v4 etc.

##JavaDoc

Java doc is provided in this project. Please find it in the 'docs' directory

The Java doc also specifies in greater detail how the matching algorithms work.

##Statistics

The most interesting part!

This program automatically logs details of every match and also the result of every match, so you can scroll through the logs if you wish and view the winning probability for each team and also their average Elo / Win rating score.

The last items printed on the log (when doing multi matching) will tell you the following:

1. The average match win/lose probability

2. The average waiting time of all players

3. The average amount of games played by all player (in this simulation)

4. The final max elo difference (see below on how my Elo algorithm works to know what this means)


Given that you can input different arguments for the matching algorithm, amount of matches, sorting and team sizes, I think you can play around quite a lot to see the different results different combinations give you.

For example:

####Example 1: 
```
java com.riotgames.interview.hongkong.matchmaking.CommandLine 100 Elo Sorted 5
```

```
AVERAGE Match Win/Lose Probability OF ALL GAMES: 0.49802739033867716
Average Waiting Time: 15.9
Average Games in Sim: 5.1
Max Elo Difference (initial is 20): 20.0
```

####Example 2: 
```
java com.riotgames.interview.hongkong.matchmaking.CommandLine 1000 Elo NonSorted 3
```
```
AVERAGE Match Win/Lose Probability OF ALL GAMES: 0.5009482015464723
Average Waiting Time: 25.85
Average Games in Sim: 30.15
Max Elo Difference (initial is 20): 20.0
```

####Example 3: 
```
java com.riotgames.interview.hongkong.matchmaking.CommandLine 100 WR Sorted 2
```
```
AVERAGE Match Win/Lose Probability OF ALL GAMES: 0.5402696246079739
Average Waiting Time: 0.0
Average Games in Sim: 5.0
Max Elo Difference (initial is 20): 20.0
```

####Example 4: 
```
java com.riotgames.interview.hongkong.matchmaking.CommandLine Elo NonSorted 5
```
```
*-- Match ---*
Team Size: 5
Average Rating: 1177.0999755859375
Team 1: 
Team Size: 5
Average Elo Rating: 1179
Average Win Rating: 0.6672969460487366
Team 2: 
Team Size: 5
Average Elo Rating: 1175
Average Win Rating: 0.6633237838745117

Probabilty of Team 1 Winning: 0.5067945817123546
Probability of Team 2 Winning: 0.4932054182876454
Team 1 Won!

```

###Example 5:
```
java com.riotgames.interview.hongkong.matchmaking.CommandLine WR Sorted 1
```
```
*-- Match ---*
Team Size: 1
Average Rating: 0.5455995202064514
Team 1: 
Team Size: 1
Average Elo Rating: 1105
Average Win Rating: 0.5935762524604797
Team 2: 
Team Size: 1
Average Elo Rating: 1009
Average Win Rating: 0.4976228177547455

Probabilty of Team 1 Winning: 0.5439669705211574
Probability of Team 2 Winning: 0.4560330294788426
Team 1 Won!
```

##Elo Algorithm - Description

The forulae I used to award (or deduct) Elo points is taken from the online wikipedia article on Elo (with some cross-referencing from other sources).

To start with, if you run a multi-match match making service, then the system will attempt to match players together where their Elo Rating is within 20 points. However, after some games are played and the player's Elo rating increases / decreases, the range of the player's ELO increases, making it more difficult to find matches with a range of 200. Therefore, if the program cannot find any matches, it will iteratively increase the range by 20 until it can start making matches again.

This typically shouldn't happen in a larger match making system. However, considering our pool has only 200 people and the total min/max range of Elo can be ~ 500 >= ~1500, this is necessary in order for the program to continue making matches. The drawback of this approach is because the Elo difference is greater, the winning probability of each team also becomes more unfair.  

#Unit Tests

You may run unit tests using the 'All Tests' build configuration target via IntelliJ
