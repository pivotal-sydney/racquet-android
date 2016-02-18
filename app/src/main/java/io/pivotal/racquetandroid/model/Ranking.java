package io.pivotal.racquetandroid.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.pivotal.racquetandroid.model.response.Player;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ranking {
    private int rank;
    private Player member = new Player();

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Player getMember() {
        return member;
    }

    public void setMember(Player member) {
        this.member = member;
    }
}
