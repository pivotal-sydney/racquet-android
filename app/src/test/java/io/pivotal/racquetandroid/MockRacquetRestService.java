package io.pivotal.racquetandroid;

import android.util.Pair;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import io.pivotal.racquetandroid.model.Club;
import io.pivotal.racquetandroid.model.request.MatchResultRequest;
import io.pivotal.racquetandroid.model.response.Matches;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Path;
import retrofit2.mock.Calls;

public class MockRacquetRestService implements RacquetRestService {
    private Queue<Pair<Object, Boolean>> responses = new ArrayDeque<>();
    private MatchResultRequest lastMatchResult;

    public void addResponse(Object response, boolean success) {
        responses.add(new Pair<>(response, success));
    }

    @Override
    public Call<List<Club>> getClubs() {
        Pair<Object, Boolean> pair = responses.peek();
        if (pair != null && pair.second) {
            return Calls.response(getListCallResponse(Club.class));
        }
        return Calls.failure(null);
    }

    @Override
    public Call<Void> postMatch(int clubId, MatchResultRequest request) {
        lastMatchResult = request;
        Pair<Object, Boolean> pair = responses.peek();
        if (pair != null && pair.second) {
            return Calls.response(getSingleCallResponse(Void.class));
        }
        return Calls.failure(null);
    }

    @Override
    public Call<Matches> getMatches(@Path("clubId") int clubId) {
        Pair<Object, Boolean> pair = responses.peek();
        if (pair != null && pair.second) {
            return Calls.response(getSingleCallResponse(Matches.class));
        }
        return Calls.failure(null);
    }

    public MatchResultRequest getLastMatchResult() {
        return lastMatchResult;
    }

    public <T> Response<List<T>> getListCallResponse(Class<T> clazz) {
        Pair<Object, Boolean> pair = responses.poll();
        List<T> responseDaturrr = (List<T>) pair.first;
        return Response.success(responseDaturrr);
    }

    public <T> Response<T> getSingleCallResponse(Class<T> clazz) {
        Pair<Object, Boolean> pair = responses.poll();
        T responseDaturrr = (T) pair.first;
        return Response.success(responseDaturrr);
    }
}
