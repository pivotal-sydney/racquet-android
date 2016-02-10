package io.pivotal.racquetandroid;

import android.util.Pair;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import io.pivotal.racquetandroid.model.Club;
import io.pivotal.racquetandroid.model.MatchResultRequest;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Path;
import retrofit2.mock.Calls;

public class MockRacquetRestService implements RacquetRestService {
    private Queue<Pair<Object, Boolean>> responses = new ArrayDeque<>();
    private MatchResultRequest lastMatchResultRequest;

    public void addResponse(Object response, boolean success) {
        responses.add(new Pair<>(response, success));
    }

    @Override
    public Call<List<Club>> getClubs() {
        return Calls.response(getListCallResponse(Club.class));
    }

    @Override
    public Call<Void> postMatch(@Path("clubId") String clubId, @Body MatchResultRequest request) {
        lastMatchResultRequest = request;
        return Calls.response(getSingleCallResponse(Void.class));
    }

    public MatchResultRequest getLastMatchResultRequest() {
        return lastMatchResultRequest;
    }

    public <T> Response<List<T>> getListCallResponse(Class<T> clazz) {
        Pair<Object, Boolean> pair = responses.poll();
        List<T> responseDaturrr = (List<T>) pair.first;
        Response<List<T>> response;
        if (pair.second) {
            response = Response.success(responseDaturrr);
        } else {
            response = Response.error(500, ResponseBody.create(MediaType.parse("application/json"), "{}"));
        }
        return response;
    }

    public <T> Response<T> getSingleCallResponse(Class<T> clazz) {
        Pair<Object, Boolean> pair = responses.poll();
        T responseDaturrr = (T) pair.first;
        Response<T> response;
        if (pair.second) {
            response = Response.success(responseDaturrr);
        } else {
            response = Response.error(500, ResponseBody.create(MediaType.parse("application/json"), "{}"));
        }
        return response;
    }
}
