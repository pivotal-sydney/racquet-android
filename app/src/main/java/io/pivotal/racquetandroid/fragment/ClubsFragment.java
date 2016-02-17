package io.pivotal.racquetandroid.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.pivotal.racquetandroid.R;
import io.pivotal.racquetandroid.RacquetApplication;
import io.pivotal.racquetandroid.RacquetRestService;
import io.pivotal.racquetandroid.adapter.ClubItemDecorator;
import io.pivotal.racquetandroid.adapter.ClubsAdapter;
import io.pivotal.racquetandroid.model.Club;
import io.pivotal.racquetandroid.model.Clubs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClubsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.list)
    RecyclerView list;

    @Inject
    RacquetRestService service;

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    ClubsAdapter adapter;

    public static ClubsFragment newInstance() {
        return new ClubsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RacquetApplication.getApplication().getApplicationComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_clubs, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        adapter = new ClubsAdapter(new ArrayList<Club>());
        list.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        list.addItemDecoration(new ClubItemDecorator(getActivity(), 3));
        swipeRefreshLayout.setOnRefreshListener(this);
        populateClubs();
    }

    private void populateClubs() {
        Call<Clubs> call = service.getClubs();
        call.enqueue(new Callback<Clubs>() {
            @Override
            public void onResponse(Call<Clubs> call, Response<Clubs> response) {
                if (response.body() != null) {
                    adapter.setClubs(response.body().getClubs());
                    list.setAdapter(adapter);
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<Clubs> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed!   " + t, Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        populateClubs();
    }
}
