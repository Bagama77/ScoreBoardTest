package com.scoreboard;

import lombok.Data;
import lombok.NonNull;

import java.util.Objects;

@Data
public class BetMatch {

    private String homeTeam;
    private String guestTeam;
    private int scoreHomeTeam;
    private int scoreGuestTeam;
    private int summaryScore;
    private long started;

    public BetMatch(@NonNull String homeTeam, @NonNull String guestTeam) {
        if (!homeTeam.isEmpty() && !guestTeam.isEmpty()) {
            this.homeTeam = homeTeam;
            this.guestTeam = guestTeam;
            this.scoreHomeTeam = 0;
            this.scoreGuestTeam = 0;
            this.summaryScore = 0;
            this.started = System.currentTimeMillis();
        } else {
            throw new RuntimeException(Message.TEAMS_NAME_NOT_NULL);
        }
    }

    public int updateSummary() {
        this.summaryScore = scoreHomeTeam + scoreGuestTeam;
        return this.summaryScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BetMatch)) return false;
        BetMatch betMatch = (BetMatch) o;
        return getScoreHomeTeam() == betMatch.getScoreHomeTeam() &&
                getScoreGuestTeam() == betMatch.getScoreGuestTeam() &&
                getSummaryScore() == betMatch.getSummaryScore() &&
                getStarted() == betMatch.getStarted() &&
                getHomeTeam().equals(betMatch.getHomeTeam()) &&
                getGuestTeam().equals(betMatch.getGuestTeam());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHomeTeam(),
                getGuestTeam(),
                getScoreHomeTeam(),
                getScoreGuestTeam(),
                getSummaryScore(),
                getStarted());
    }

    @Override
    public String toString() {
        return
                "homeTeam='" + homeTeam + '\'' +
                        ", guestTeam='" + guestTeam + '\'' +
                        ", scoreHomeTeam=" + scoreHomeTeam +
                        ", scoreGuestTeam=" + scoreGuestTeam +
                        ", summaryScore=" + summaryScore +
                        "} \r\n";
    }
}
