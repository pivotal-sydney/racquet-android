package io.pivotal.racquetandroid.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Player {
    private String name = "";
    private String twitterHandle = "";
    private String profileImageUrl = "";
    private int wins;
    private int losses;
    private int points;
    private long winningPercentage;
    private boolean trendingUp;

    public boolean isTreandingDown() {
        return treandingDown;
    }

    public void setTreandingDown(boolean treandingDown) {
        this.treandingDown = treandingDown;
    }

    public boolean isTrendingUp() {
        return trendingUp;
    }

    public void setTrendingUp(boolean trendingUp) {
        this.trendingUp = trendingUp;
    }

    public long getWinningPercentage() {
        return winningPercentage;
    }

    public void setWinningPercentage(long winningPercentage) {
        this.winningPercentage = winningPercentage;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    private boolean treandingDown;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTwitterHandle() {
        return twitterHandle;
    }

    public void setTwitterHandle(String twitterHandle) {
        this.twitterHandle = twitterHandle;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
