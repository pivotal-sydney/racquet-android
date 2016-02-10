package io.pivotal.racquetandroid.adapter;

import android.widget.LinearLayout;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

import io.pivotal.racquetandroid.BuildConfig;
import io.pivotal.racquetandroid.model.Club;
import io.pivotal.racquetandroid.util.ModelBuilder;

import static org.assertj.android.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

@Config(constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class ClubAdapterTest {

    private ClubAdapter adapter;

    @Test
    public void onCreate_andBindsData() {
        List<Club> clubs = ModelBuilder.getClubs(1, "Club Name");
        adapter = new ClubAdapter(clubs);

        ClubAdapter.ClubViewHolder holder = adapter.onCreateViewHolder(new LinearLayout(RuntimeEnvironment.application), 0);
        adapter.onBindViewHolder(holder, 0);

        assertThat(holder.name).hasText("Club Name0");
    }

    @Test
    public void itemCount_matchesDataSize() {
        List<Club> clubs = ModelBuilder.getClubs(3, "Club Name");
        adapter = new ClubAdapter(clubs);
        assertThat(adapter.getItemCount()).isEqualTo(3);
    }
}