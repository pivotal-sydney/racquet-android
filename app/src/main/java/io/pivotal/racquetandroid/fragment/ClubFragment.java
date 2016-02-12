package io.pivotal.racquetandroid.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.pivotal.racquetandroid.R;
import io.pivotal.racquetandroid.RacquetApplication;
import io.pivotal.racquetandroid.RacquetRestService;
import io.pivotal.racquetandroid.adapter.MatchesAdapter;
import io.pivotal.racquetandroid.model.Club;
import io.pivotal.racquetandroid.model.request.MatchResult;
import io.pivotal.racquetandroid.model.request.MatchResultRequest;
import io.pivotal.racquetandroid.model.response.Match;
import io.pivotal.racquetandroid.model.response.Matches;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClubFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private static String CLUB_KEY = "club_name_key";
    @Bind(R.id.loser)
    EditText loser;

    @Bind(R.id.winner)
    EditText winner;

    @Bind(R.id.submit)
    ImageButton submit;

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.list)
    RecyclerView list;

    @Inject
    RacquetRestService racquetRestService;

    private Club club;

    MatchesAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RacquetApplication.getApplication().getApplicationComponent().inject(this);
        club = (Club) getArguments().getSerializable(CLUB_KEY);
        adapter = new MatchesAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_club, null);
    }

    @Override
    public void onResume() {
        super.onResume();
        populateResults();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MatchResult matchResult = new MatchResult();
                matchResult.setWinner(winner.getText().toString());
                matchResult.setLoser(loser.getText().toString());
                Call<Match> call = racquetRestService.postMatch(club.getId(), new MatchResultRequest(matchResult));
                call.enqueue(new Callback<Match>() {
                    @Override
                    public void onResponse(Call<Match> call, Response<Match> response) {
                        if (response.isSuccess()) {
                            Toast.makeText(getActivity(), "Successfully posted match!", Toast.LENGTH_SHORT).show();
                            adapter.addMatch(response.body());
                        } else {
                            Toast.makeText(getActivity(), "Failed to post match: " + response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Match> call, Throwable t) {

                    }
                });
            }
        });
        list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        swipeRefreshLayout.setOnRefreshListener(this);
    }



    void populateResults() {
        Call<Matches> call = racquetRestService.getMatches(club.getId());
        call.enqueue(new Callback<Matches>() {
            @Override
            public void onResponse(Call<Matches> call, Response<Matches> response) {
                adapter.setMatches(response.body());
                list.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<Matches> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to fetch match results", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public static ClubFragment newInstance(Club club) {
        ClubFragment fragment = new ClubFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CLUB_KEY, club);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onRefresh() {
        populateResults();
    }
}
