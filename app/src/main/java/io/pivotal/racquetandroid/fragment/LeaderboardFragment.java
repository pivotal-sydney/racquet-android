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

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.pivotal.racquetandroid.R;
import io.pivotal.racquetandroid.RacquetApplication;
import io.pivotal.racquetandroid.RacquetRestService;
import io.pivotal.racquetandroid.adapter.LeaderboardAdapter;
import io.pivotal.racquetandroid.model.Club;
import io.pivotal.racquetandroid.model.Leaderboard;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaderboardFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String CLUB_KEY = "club_key";

    LeaderboardAdapter adapter;

    @Inject
    RacquetRestService racquetRestService;
    private Club club;

    @Bind(R.id.list)
    RecyclerView list;

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    public static LeaderboardFragment newInstance(Club club) {
        LeaderboardFragment fragment = new LeaderboardFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CLUB_KEY, club);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RacquetApplication.getApplication().getApplicationComponent().inject(this);
        club = (Club) getArguments().getSerializable(CLUB_KEY);
        adapter = new LeaderboardAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_leaderboard, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        swipeRefreshLayout.setOnRefreshListener(this);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        populateLeaderboard();
    }

    private void populateLeaderboard() {
        Call<Leaderboard> call = racquetRestService.getLeaderboard(club.getId());
        call.enqueue(new Callback<Leaderboard>() {
            @Override
            public void onResponse(Call<Leaderboard> call, Response<Leaderboard> response) {
                if (response.isSuccess()) {
                    adapter.setLeaderboard(response.body());
                    list.setAdapter(adapter);
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<Leaderboard> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        populateLeaderboard();
    }
}
