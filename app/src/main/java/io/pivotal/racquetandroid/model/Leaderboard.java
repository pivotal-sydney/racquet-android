package io.pivotal.racquetandroid.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Leaderboard {
    private List<Ranking> majors = new ArrayList<>();
    private List<Ranking> minors = new ArrayList<>();

    public List<Ranking> getMinors() {
        return minors;
    }

    public void setMinors(List<Ranking> minors) {
        this.minors = minors;
    }

    public List<Ranking> getMajors() {
        return majors;
    }

    public void setMajors(List<Ranking> majors) {
        this.majors = majors;
    }

}
