package io.pivotal.racquetandroid.adapter;

import android.content.Intent;
import android.widget.LinearLayout;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import java.util.List;

import io.pivotal.racquetandroid.BuildConfig;
import io.pivotal.racquetandroid.activity.ClubActivity;
import io.pivotal.racquetandroid.model.Club;
import io.pivotal.racquetandroid.util.ModelBuilder;

import static org.assertj.android.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

@Config(constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class ClubsAdapterTest {

    private ClubsAdapter adapter;

    @Test
    public void onCreate_andBindsData() {
        List<Club> clubs = ModelBuilder.getClubs(1, "Club Name");
        adapter = new ClubsAdapter(clubs);

        ClubsAdapter.ClubViewHolder holder = adapter.onCreateViewHolder(new LinearLayout(RuntimeEnvironment.application), 0);
        adapter.onBindViewHolder(holder, 0);

        assertThat(holder.name).hasText("Club Name0");
    }

    @Test
    public void onClick_startsClubActivity() {
        List<Club> clubs = ModelBuilder.getClubs(3, "Club Name");
        adapter = new ClubsAdapter(clubs);

        ClubsAdapter.ClubViewHolder holder = adapter.onCreateViewHolder(new LinearLayout(RuntimeEnvironment.application), 1);
        adapter.onBindViewHolder(holder, 1);
        holder.itemView.performClick();

        Intent intent = ShadowApplication.getInstance().getNextStartedActivity();
        assertThat(intent.getComponent().getClassName()).isEqualTo(ClubActivity.class.getCanonicalName());
        assertThat((Club) intent.getExtras().getSerializable(ClubActivity.CLUB_KEY)).isEqualTo(clubs.get(1));
    }

    @Test
    public void itemCount_matchesDataSize() {
        List<Club> clubs = ModelBuilder.getClubs(3, "Club Name");
        adapter = new ClubsAdapter(clubs);
        assertThat(adapter.getItemCount()).isEqualTo(3);
    }
}