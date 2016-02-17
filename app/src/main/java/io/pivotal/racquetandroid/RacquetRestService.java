package io.pivotal.racquetandroid;

import java.util.List;

import io.pivotal.racquetandroid.model.Clubs;
import io.pivotal.racquetandroid.model.request.MatchResultRequest;
import io.pivotal.racquetandroid.model.response.Match;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RacquetRestService {
    @GET("/api/clubs")
    Call<Clubs> getClubs();

    @POST("/api/{clubId}/matches")
    Call<Match> postMatch(@Path("clubId") int clubId, @Body MatchResultRequest request);

    @GET("/api/{clubId}/matches")
    Call<List<Match>> getMatches(@Path("clubId") int clubSlug);
}
