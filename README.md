Task requirements:
You are working in a sports data company, and we would like you to develop a new Live Football World Cup Scoreboard library that shows all the ongoing matches and their scores.
The scoreboard supports the following operations:

1. Start a new match, assuming initial score 0 – 0 and adding it the scoreboard. This should capture following parameters:
a. Home team
b. Away team

2. Update score. This should receive a pair of absolute scores: home team score and away team score.

3. Finish match currently in progress. This removes a match from the scoreboard.

4. Get a summary of matches in progress ordered by their total score. The matches with the
same total score will be returned ordered by the most recently started match in the scoreboard.
For example, if following matches are started in the specified order and their scores respectively updated:
a. Mexico 0 - Canada 5 b. Spain 10 - Brazil 2
c. Germany 2 - France 2 d. Uruguay 6 - Italy 6
e. Argentina 3 - Australia 1

The summary should be as follows:
1. Uruguay 6 - Italy 6
2. Spain 10 - Brazil 2
3. Mexico 0 - Canada 5
4. Argentina 3 - Australia 1
5. Germany 2 - France 2


# main.ScoreBoard
main.ScoreBoard

When starting the match there is checking that any team in new match already was in the
list of current matches. If so, match will not started and there will be warning "There is already has a match with this team - <teamName>"

When starting the match there is checking for null or empty team names - it is forbidden team with empty name. Match will not start.

There are also checks when trying to update the score - if there is no such team in current matches - score will not be updated.
And there are will be warning like "Score is not updated for the Match <homeTeam> - <guestTeam>".

There are also checks when trying to finish the match - if there is no such team in current matches - match will not be found
and finished.
And there are will be warning like "Cannot finish the match with <team>. Match is not found.".

To update the score just put the one of the team and scores in order: <team>, <scoreHomeTeam>, <scoreGuestTeam>. Score will not
be updated when the <scoreHomeTeam> or <scoreGuestTeam> will be less then zero or less then previous scores.

Teams names could be lower or in Upper case, or something like "NEw yOrK", matters only characters.

If you want remove the match just put in arguments one of match's teams.
