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
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.pivotal.racquetandroid.R;
import io.pivotal.racquetandroid.RacquetApplication;
import io.pivotal.racquetandroid.RacquetRestService;
import io.pivotal.racquetandroid.adapter.MatchesAdapter;
import io.pivotal.racquetandroid.event.MatchUpdatedEvent;
import io.pivotal.racquetandroid.model.Club;
import io.pivotal.racquetandroid.model.response.Match;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private static String CLUB_KEY = "club_name_key";
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.list)
    RecyclerView list;

    @Inject
    RacquetRestService racquetRestService;

    @Inject
    Bus bus;

    private Club club;

    MatchesAdapter adapter;
    private EventHandler eventHandler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RacquetApplication.getApplication().getApplicationComponent().inject(this);
        club = (Club) getArguments().getSerializable(CLUB_KEY);
        adapter = new MatchesAdapter();
        eventHandler = new EventHandler();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, null);
    }

    @Override
    public void onResume() {
        super.onResume();
        bus.register(eventHandler);
        populateResults();
    }

    @Override
    public void onPause() {
        bus.unregister(eventHandler);
        super.onPause();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    void populateResults() {
        Call<List<Match>> call = racquetRestService.getMatches(club.getId());
        call.enqueue(new Callback<List<Match>>() {
            @Override
            public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {
                adapter.setMatches(response.body());
                list.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Match>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to fetch match results", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public static FeedFragment newInstance(Club club) {
        FeedFragment fragment = new FeedFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CLUB_KEY, club);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onRefresh() {
        populateResults();
    }

    class EventHandler {
        @Subscribe
        @SuppressWarnings("unused")
        public void onMatchUpdatedEvent(MatchUpdatedEvent event) {
            Match match = event.getMatch();
            if (match != null) {
                adapter.addMatch(match);
            }
        }
    }
}
