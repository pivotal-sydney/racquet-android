package io.pivotal.racquetandroid.fragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import java.util.List;

import javax.inject.Inject;

import io.pivotal.racquetandroid.BuildConfig;
import io.pivotal.racquetandroid.MockRacquetRestService;
import io.pivotal.racquetandroid.RacquetApplication;
import io.pivotal.racquetandroid.RacquetRestService;
import io.pivotal.racquetandroid.TestApplicationComponent;
import io.pivotal.racquetandroid.model.Club;
import io.pivotal.racquetandroid.model.Ranking;
import io.pivotal.racquetandroid.util.ModelBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@Config(constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class LeaderboardFragmentTest {

    private LeaderboardFragment fragment;

    @Inject
    RacquetRestService racquetRestService;

    MockRacquetRestService mockRacquetRestService;

    @Before
    public void setup() {
        Club club = ModelBuilder.getClub(1, "name");
        ((TestApplicationComponent) RacquetApplication.getApplication().getApplicationComponent()).inject(this);
        fragment = LeaderboardFragment.newInstance(club);
        mockRacquetRestService = (MockRacquetRestService) racquetRestService;
    }

    @Test
    public void onViewCreated_shouldFetchLeaderboard() {
        List<Ranking> majors = ModelBuilder.getRankings(5);
        mockRacquetRestService.addResponse(ModelBuilder.getLeaderboard(majors, null), true);

        SupportFragmentTestUtil.startFragment(fragment);

        assertThat(fragment.adapter.getItemCount()).isEqualTo(7);
    }

    @Test
    public void onRefresh_shouldRefetchLeaderboard() {
        SupportFragmentTestUtil.startFragment(fragment);
        assertThat(fragment.adapter.getItemCount()).isEqualTo(2);
        List<Ranking> majors = ModelBuilder.getRankings(5);
        mockRacquetRestService.addResponse(ModelBuilder.getLeaderboard(majors, null), true);
        fragment.onRefresh();
        assertThat(fragment.adapter.getItemCount()).isEqualTo(7);
    }

}