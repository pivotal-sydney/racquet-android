package io.pivotal.racquetandroid.event;

import io.pivotal.racquetandroid.model.response.Match;

public class MatchUpdatedEvent {
    private Match match;

    public MatchUpdatedEvent(Match match) {
        this.match = match;
    }
    public Match getMatch() {
        return match;
    }
}
