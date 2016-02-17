package io.pivotal.racquetandroid.adapter;

import android.content.Intent;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import java.util.List;

import javax.inject.Inject;

import io.pivotal.racquetandroid.BuildConfig;
import io.pivotal.racquetandroid.RacquetApplication;
import io.pivotal.racquetandroid.TestApplicationComponent;
import io.pivotal.racquetandroid.activity.ClubActivity;
import io.pivotal.racquetandroid.model.Club;
import io.pivotal.racquetandroid.util.ModelBuilder;

import static org.assertj.android.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@Config(constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class ClubsAdapterTest {

    @Inject
    Picasso picasso;

    private ClubsAdapter adapter;

    @Before
    public void setup() {
        ((TestApplicationComponent) RacquetApplication.getApplication().getApplicationComponent()).inject(this);
    }

    @Test
    public void onCreate_andBindsData() {
        List<Club> clubs = ModelBuilder.getClubs(1, "Club Name").getClubs();
        adapter = new ClubsAdapter(clubs);

        ClubsAdapter.ClubViewHolder holder = adapter.onCreateViewHolder(new LinearLayout(RuntimeEnvironment.application), 0);
        adapter.onBindViewHolder(holder, 0);

        assertThat(holder.profileView.getProfileName()).hasText("Club Name0");
    }

    @Test
    public void onClick_startsClubActivity() {
        List<Club> clubs = ModelBuilder.getClubs(3, "Club Name").getClubs();
        adapter = new ClubsAdapter(clubs);

        ClubsAdapter.ClubViewHolder holder = adapter.onCreateViewHolder(new LinearLayout(RuntimeEnvironment.application), 1);
        adapter.onBindViewHolder(holder, 1);
        holder.itemView.performClick();

        Intent intent = ShadowApplication.getInstance().getNextStartedActivity();
        assertThat(intent.getComponent().getClassName()).isEqualTo(ClubActivity.class.getCanonicalName());
        assertThat((Club) intent.getExtras().getSerializable(ClubActivity.CLUB_KEY)).isEqualTo(clubs.get(1));
        verify(picasso).load(clubs.get(1).getLogo().getStandard().getUrl());
    }

    @Test
    public void itemCount_matchesDataSize() {
        List<Club> clubs = ModelBuilder.getClubs(3, "Club Name").getClubs();
        adapter = new ClubsAdapter(clubs);
        assertThat(adapter.getItemCount()).isEqualTo(3);
    }
}