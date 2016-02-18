package io.pivotal.racquetandroid.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.pivotal.racquetandroid.R;
import io.pivotal.racquetandroid.RacquetApplication;
import io.pivotal.racquetandroid.adapter.ClubPagerAdapter;
import io.pivotal.racquetandroid.event.DismissRecordMatchEvent;
import io.pivotal.racquetandroid.event.FetchedMatchesEvent;
import io.pivotal.racquetandroid.event.MatchUpdatedEvent;
import io.pivotal.racquetandroid.model.Club;
import io.pivotal.racquetandroid.model.response.Match;
import io.pivotal.racquetandroid.model.response.Player;
import io.pivotal.racquetandroid.view.RecordMatchView;

public class ClubActivity extends AppCompatActivity {

    public static final String CLUB_KEY = "club_key";

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.pager)
    ViewPager pager;

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;

    @Bind(R.id.fab)
    FloatingActionButton button;

    @Bind(R.id.record_match)
    RecordMatchView recordMatchView;

    @Inject
    Bus bus;

    private ClubPagerAdapter adapter;

    private EventHandler eventHandler;

    Set<Player> players = new HashSet<>();

    AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            recordMatchView.setVisibility(View.INVISIBLE);
            button.show();
        }
    };

    public static void startActivity(Context context, Club club) {
        Intent intent = getIntent(context, club);
        context.startActivity(intent);
    }

    public static Intent getIntent(Context context, Club club) {
        Intent intent = new Intent(context, ClubActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(CLUB_KEY, club);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RacquetApplication.getApplication().getApplicationComponent().inject(this);
        Club club = (Club) getIntent().getExtras().getSerializable(CLUB_KEY);
        setContentView(R.layout.activity_club);
        ButterKnife.bind(this);

        recordMatchView.setClub(club);

        //noinspection ConstantConditions
        toolbar.setTitle(club.getName());
        setSupportActionBar(toolbar);

        eventHandler = new EventHandler();

        adapter = new ClubPagerAdapter(club, getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager));
        tabLayout.setTabsFromPagerAdapter(adapter);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecordMatch();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        bus.register(eventHandler);
    }

    @Override
    protected void onPause() {
        bus.unregister(eventHandler);
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if (recordMatchView.getVisibility() == View.VISIBLE) {
            hideRecordMatch();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showRecordMatch() {
        recordMatchView.setVisibility(View.VISIBLE);
        ViewAnimationUtils.createCircularReveal(recordMatchView, (button.getLeft() + button.getRight()) / 2, (button.getBottom() + button.getTop()) / 2, 0, Math.max(recordMatchView.getWidth(), recordMatchView.getHeight())).setDuration(300).start();
        button.hide();
    }

    private void hideRecordMatch() {
        Animator reveal = ViewAnimationUtils.createCircularReveal(recordMatchView, (button.getLeft() + button.getRight()) / 2, (button.getBottom() + button.getTop()) / 2, Math.max(recordMatchView.getWidth(), recordMatchView.getHeight()), 0).setDuration(300);
        reveal.addListener(animatorListenerAdapter);
        reveal.start();
    }

    private void populatePlayers(List<Match> matches) {
        for (int i = 0; i < matches.size(); i++) {
            players.add(matches.get(i).getWinner());
            players.add(matches.get(i).getLoser());
        }
        recordMatchView.setPlayers(players);
    }

    class EventHandler {
        @Subscribe
        @SuppressWarnings("unused")
        public void onDismissRecordMatchEvent(DismissRecordMatchEvent event) {
            hideRecordMatch();
        }

        @Subscribe
        @SuppressWarnings("unused")
        public void onMatchUpdatedEvent(MatchUpdatedEvent event) {
            players.add(event.getMatch().getWinner());
            players.add(event.getMatch().getLoser());
        }

        @Subscribe
        @SuppressWarnings("unused")
        public void onFetchedMatchesEvent(FetchedMatchesEvent event) {
            populatePlayers(event.getMatches());
        }
    }
}
