package io.pivotal.racquetandroid.adapter;

import android.support.v4.app.Fragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import io.pivotal.racquetandroid.BuildConfig;
import io.pivotal.racquetandroid.fragment.FeedFragment;
import io.pivotal.racquetandroid.fragment.LeaderboardFragment;
import io.pivotal.racquetandroid.model.Club;
import io.pivotal.racquetandroid.util.ModelBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@Config(constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class ClubPagerAdapterTest {

    private ClubPagerAdapter adapter;

    @Before
    public void setup() {
        Club club = ModelBuilder.getClub(1, "name");
        adapter = new ClubPagerAdapter(club, null);
    }

    @Test
    public void getCount_returnsCorrectSize() {
        assertThat(adapter.getCount()).isEqualTo(2);
    }

    @Test
    public void getItem_returnsCorrectFragment() {
        Fragment fragment = adapter.getItem(0);
        assertThat(fragment).isInstanceOf(FeedFragment.class);

        fragment = adapter.getItem(1);
        assertThat(fragment).isInstanceOf(LeaderboardFragment.class);
    }

    @Test
    public void getPageTitle_returnsCorrectTitle() {
        assertThat(adapter.getPageTitle(0)).isEqualTo("Feed");
        assertThat(adapter.getPageTitle(1)).isEqualTo("Leaderboard");
    }

}