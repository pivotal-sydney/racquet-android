package io.pivotal.racquetandroid.model.request;

public class MatchResultRequest {
    private MatchResult match;

    public MatchResultRequest(MatchResult match) {
        this.match = match;
    }

    public MatchResult getMatch() {
        return match;
    }

    public void setMatch(MatchResult match) {
        this.match = match;
    }
}
