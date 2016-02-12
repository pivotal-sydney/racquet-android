package io.pivotal.racquetandroid.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Match {
    private Player winnerData = new Player();
    private Player loserData = new Player();
    private int points;

    public Player getWinnerData() {
        return winnerData;
    }

    public void setWinnerData(Player winnerData) {
        this.winnerData = winnerData;
    }

    public Player getLoserData() {
        return loserData;
    }

    public void setLoserData(Player loserData) {
        this.loserData = loserData;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
