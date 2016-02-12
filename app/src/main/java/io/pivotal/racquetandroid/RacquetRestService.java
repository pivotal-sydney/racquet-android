package io.pivotal.racquetandroid;

import java.util.List;

import io.pivotal.racquetandroid.model.Club;
import io.pivotal.racquetandroid.model.request.MatchResultRequest;
import io.pivotal.racquetandroid.model.response.Match;
import io.pivotal.racquetandroid.model.response.Matches;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RacquetRestService {
    @GET("/clubs.json")
    Call<List<Club>> getClubs();

    @POST("/api/{clubId}/matches.json")
    Call<Match> postMatch(@Path("clubId") int clubId, @Body MatchResultRequest request);

    @GET("/api/{clubId}/matches.json")
    Call<Matches> getMatches(@Path("clubId") int clubSlug);
}
