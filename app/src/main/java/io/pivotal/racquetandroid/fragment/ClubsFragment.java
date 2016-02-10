package io.pivotal.racquetandroid.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.pivotal.racquetandroid.R;
import io.pivotal.racquetandroid.RacquetApplication;
import io.pivotal.racquetandroid.RacquetRestService;
import io.pivotal.racquetandroid.adapter.ClubsAdapter;
import io.pivotal.racquetandroid.model.Club;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClubsFragment extends Fragment {

    @Bind(R.id.list)
    RecyclerView list;

    @Inject
    RacquetRestService service;

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
        Call<List<Club>> call = service.getClubs();
        call.enqueue(new Callback<List<Club>>() {
            @Override
            public void onResponse(Call<List<Club>> call, Response<List<Club>> response) {
                if (response.body() != null) {
                    adapter = new ClubsAdapter(response.body());
                    list.setAdapter(adapter);
                    list.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                }
            }

            @Override
            public void onFailure(Call<List<Club>> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed!   " + t, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
