package io.pivotal.racquetandroid.activity;

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

import butterknife.Bind;
import butterknife.ButterKnife;
import io.pivotal.racquetandroid.R;
import io.pivotal.racquetandroid.adapter.ClubPagerAdapter;
import io.pivotal.racquetandroid.model.Club;
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

    private ClubPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Club club = (Club) getIntent().getExtras().getSerializable(CLUB_KEY);
        setContentView(R.layout.activity_club);
        ButterKnife.bind(this);

        recordMatchView.setClub(club);

        //noinspection ConstantConditions
        toolbar.setTitle(club.getName());
        setSupportActionBar(toolbar);

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

    private void showRecordMatch() {
        recordMatchView.setVisibility(View.VISIBLE);
        ViewAnimationUtils.createCircularReveal(recordMatchView, (button.getLeft() + button.getRight()) / 2, (button.getBottom() + button.getTop()) / 2, 0, Math.max(recordMatchView.getWidth(), recordMatchView.getHeight())).setDuration(400).start();
        button.hide();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

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
}
