import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.java.Log;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Log
@Getter
@Setter
public class ScoreBoard implements ScoreboardActivity {

    private List<BetMatch> matches;
    private List<String> teams;

    public ScoreBoard() {
        this.matches = new CopyOnWriteArrayList<>();
        this.teams = new CopyOnWriteArrayList<>();
    }

    @Override
    public BetMatch startAMatch(String homeTeam, String guestTeam) {

        try {
            if (homeTeam == null || guestTeam == null) {
                log.warning(Message.TEAMS_NAME_NOT_NULL);
                return null;
            } else if (containsMatchWithTeam(homeTeam)) {
                log.warning(String.format(Message.ALREADY_HAS_A_MATCH, homeTeam));
                return null;
            } else if (containsMatchWithTeam(guestTeam)) {
                log.warning(String.format(Message.ALREADY_HAS_A_MATCH, guestTeam));
                return null;
            } else if (homeTeam.equalsIgnoreCase(guestTeam)) {
                log.warning(Message.TEAMS_NAMES_SHOULD_NOT_BE_SIMILAR);
                return null;
            } else {
                try {
                    BetMatch newBetMatch = new BetMatch(homeTeam, guestTeam);
                    this.matches.add(newBetMatch);
                    this.teams.add(homeTeam.toLowerCase());
                    this.teams.add(guestTeam.toLowerCase());
                    log.info(newBetMatch.toString());
                    return newBetMatch;
                } catch (UnsupportedOperationException e) {
                    log.severe(String.format(Message.CANNOT_ADD_A_MATCH, Arrays.asList(e.getStackTrace())));
                    return null;
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
        }


    @Override
    public boolean updateScore(@NonNull String team, int homeTeamScore, int guestTeamScore) {
        AtomicBoolean result = new AtomicBoolean(false);
        if (!this.containsMatchWithTeam(team) || team.isEmpty()) {
            log.warning(String.format(Message.NO_SUCH_MATCH_WITH_TEAM, team));
            result.set(false);
        } else {
            matches.forEach(betMatch -> {
                if (betMatch.getHomeTeam().equalsIgnoreCase(team)
                        || betMatch.getGuestTeam().equalsIgnoreCase(team)) {

                    int oldScoreHomeTeam = betMatch.getScoreHomeTeam();
                    int oldScoreGuestTeam = betMatch.getScoreGuestTeam();

                    if (oldScoreHomeTeam <= homeTeamScore && oldScoreGuestTeam <= guestTeamScore
                            && ((homeTeamScore - oldScoreHomeTeam) == 1 || (guestTeamScore - oldScoreGuestTeam) == 1)) {
                        betMatch.setScoreHomeTeam(homeTeamScore);
                        betMatch.setScoreGuestTeam(guestTeamScore);
                        betMatch.updateSummary();
                        result.set(true);
                    } else {
                        log.warning(String.format(Message.SCORE_IS_NOT_UPDATED_FOR_THE_MATCH, betMatch.getHomeTeam(),
                                betMatch.getGuestTeam()));
                    }
                    log.info(betMatch.toString());
                }
            });
        }
        return result.get();
    }

    @Override
    public boolean finishMatch(String team) {
        AtomicBoolean result = new AtomicBoolean(false);
        if (!this.containsMatchWithTeam(team)) {
            log.warning(String.format(Message.NO_SUCH_MATCH_WITH_TEAM, team));
        } else {
            matches.forEach(betMatch -> {
                try {
                    if (betMatch.getHomeTeam().equalsIgnoreCase(team)
                            || betMatch.getGuestTeam().equalsIgnoreCase(team)) {
                        matches.remove(betMatch);
                        log.info(String.format(Message.MATCH_IS_FINISHED_SCORE_IS,
                                betMatch.getHomeTeam(),
                                betMatch.getScoreHomeTeam(),
                                betMatch.getGuestTeam(),
                                betMatch.getScoreGuestTeam()));
                        result.set(true);
                    }
                } catch (Exception e) {
                    log.severe(String.format(Message.CANNOT_FINISH_THE_MATCH, Arrays.asList(e.getStackTrace())));
                }
            });
        }
        return result.get();
    }

    @Override
    public String getSummary() {

        matches.sort((o1, o2) -> {
            if (o1.getSummaryScore() == o2.getSummaryScore()) {
                if(o1.getStarted() > o2.getStarted()) {
                    return -1;
                } else if(o1.getSummaryScore() == o2.getSummaryScore()) {
                    return 1;
                } else return 0;
            } else {
                if (o1.getSummaryScore() > o2.getSummaryScore()) {
                    return -1;
                } else if (o1.getSummaryScore() < o2.getSummaryScore()) {
                    return 1;
                }
                return 0;
            }
        });

        StringBuilder summary = new StringBuilder(Message.SUMMARY);
        AtomicInteger order = new AtomicInteger();
        matches.forEach(matche ->{
            summary.append(Message.TAB + order.addAndGet(1) + Message.DOT_SPACE);
            summary.append(matche.getHomeTeam());
            summary.append(Message.SPACE + matche.getScoreHomeTeam());
            summary.append(Message.TIRE);
            summary.append(matche.getGuestTeam());
            summary.append(Message.SPACE + matche.getScoreGuestTeam());
            summary.append(Message.RN);
        });

        System.out.println(summary.toString());
        return summary.toString();
    }

    @Override
    public String toString() {
        return "main.ScoreBoard{" +
                "matches=" + matches +
                ", teams=" + teams +
                '}';
    }

    private boolean containsMatchWithTeam(String team) {
        return this.teams.contains(team.toLowerCase());
    }
}

