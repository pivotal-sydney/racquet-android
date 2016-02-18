package io.pivotal.racquetandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.otto.Bus;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.pivotal.racquetandroid.R;
import io.pivotal.racquetandroid.RacquetApplication;
import io.pivotal.racquetandroid.RacquetRestService;
import io.pivotal.racquetandroid.adapter.FilteredPlayerAdapter;
import io.pivotal.racquetandroid.event.DismissRecordMatchEvent;
import io.pivotal.racquetandroid.event.MatchUpdatedEvent;
import io.pivotal.racquetandroid.model.Club;
import io.pivotal.racquetandroid.model.request.MatchResult;
import io.pivotal.racquetandroid.model.request.MatchResultRequest;
import io.pivotal.racquetandroid.model.response.Match;
import io.pivotal.racquetandroid.model.response.Player;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecordMatchView extends LinearLayout {

    @Bind(R.id.winner)
    AutoCompleteTextView winner;

    @Bind(R.id.loser)
    AutoCompleteTextView loser;

    @Bind(R.id.submit)
    Button submit;

    @Bind(R.id.cancel)
    ImageButton cancel;

    @Inject
    RacquetRestService racquetRestService;

    @Inject
    Bus bus;

    private Club club;

    private FilteredPlayerAdapter adapter;

    public RecordMatchView(Context context) {
        super(context);
        init();
    }

    public RecordMatchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setClub(Club club) {
        this.club = club;
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_record_match, this);
        ButterKnife.bind(this);
        RacquetApplication.getApplication().getApplicationComponent().inject(this);

        adapter = new FilteredPlayerAdapter(new HashSet<Player>());
        winner.setAdapter(adapter);
        loser.setAdapter(adapter);

        setupListeners();
    }


    public void setPlayers(Set<Player> players) {
        adapter.setPlayers(players);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility == VISIBLE) {
            winner.requestFocus();
            InputMethodManager keyboard = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            keyboard.showSoftInput(winner, 0);
            super.setVisibility(visibility);
        } else {
            super.setVisibility(visibility);
            InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindowToken(), 0);
        }
    }

    private void setupListeners() {
        winner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Player player = (Player) adapter.getItem(position);
                winner.setText(view.getContext().getString(R.string.twitter_handle, player.getTwitterHandle()));
                loser.requestFocus();
            }
        });

        loser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Player player = (Player) adapter.getItem(position);
                loser.setText(view.getContext().getString(R.string.twitter_handle, player.getTwitterHandle()));
                loser.clearFocus();
            }
        });

        submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                bus.post(new DismissRecordMatchEvent());
                MatchResult matchResult = new MatchResult();
                matchResult.setWinner(winner.getText().toString());
                matchResult.setLoser(loser.getText().toString());
                Call<Match> call = racquetRestService.postMatch(club.getId(), new MatchResultRequest(matchResult));
                call.enqueue(new Callback<Match>() {
                    @Override
                    public void onResponse(Call<Match> call, Response<Match> response) {
                        if (response.isSuccess()) {
                            Toast.makeText(getContext(), "Successfully posted match!", Toast.LENGTH_SHORT).show();
                            bus.post(new MatchUpdatedEvent(response.body()));
                        } else {
                            Toast.makeText(getContext(), "Failed to post match: " + response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Match> call, Throwable t) {
                    }
                });
                winner.setText("");
                loser.setText("");
            }
        });

        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                bus.post(new DismissRecordMatchEvent());
            }
        });
    }
}
