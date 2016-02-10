package io.pivotal.racquetandroid;

import java.util.List;

import io.pivotal.racquetandroid.model.Club;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RacquetRestService {
    @GET("/clubs.json")
    Call<List<Club>> getClubs();
}
