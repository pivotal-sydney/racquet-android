package io.pivotal.racquetandroid.event;

import java.util.List;

import io.pivotal.racquetandroid.model.response.Match;

public class FetchedMatchesEvent {
    private List<Match> body;

    public FetchedMatchesEvent(List<Match> body) {
        this.body = body;
    }

    public List<Match> getMatches() {
        return body;
    }
}
