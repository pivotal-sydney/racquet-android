package io.pivotal.racquetandroid.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.pivotal.racquetandroid.R;
import io.pivotal.racquetandroid.fragment.ClubFragment;

public class ClubActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content, ClubFragment.newInstance()).commit();
        }
        setContentView(R.layout.activity_club);
    }
}
