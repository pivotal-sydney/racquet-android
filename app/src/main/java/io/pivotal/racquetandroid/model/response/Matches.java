package io.pivotal.racquetandroid.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Matches {
    private List<Match> results = new ArrayList<>();

    public List<Match> getResults() {
        return results;
    }

    public void setResults(List<Match> results) {
        this.results = results;
    }
}
