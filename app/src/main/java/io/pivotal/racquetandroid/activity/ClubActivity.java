package io.pivotal.racquetandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.pivotal.racquetandroid.model.Club;

public class ClubActivity extends AppCompatActivity {
    public static final String CLUB_KEY = "club_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Club club = (Club) getIntent().getExtras().getSerializable(CLUB_KEY);
        setTitle(club.getName());
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
