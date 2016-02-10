package io.pivotal.racquetandroid;

import java.util.List;

import io.pivotal.racquetandroid.model.Club;
import io.pivotal.racquetandroid.model.MatchResultRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RacquetRestService {
    @GET("/clubs.json")
    Call<List<Club>> getClubs();

    @POST("/{clubId}/matches.json")
    Call<Void> postMatch(@Path("clubId") String clubId, @Body MatchResultRequest request);
}
