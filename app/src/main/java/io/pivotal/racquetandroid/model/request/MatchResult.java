package io.pivotal.racquetandroid.model.request;

public class MatchResult {

    private String winner;
    private String loser;

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getLoser() {
        return loser;
    }

    public void setLoser(String loser) {
        this.loser = loser;
    }
}
