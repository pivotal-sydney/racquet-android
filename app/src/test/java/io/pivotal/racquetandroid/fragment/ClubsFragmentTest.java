package io.pivotal.racquetandroid.fragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.pivotal.racquetandroid.BuildConfig;
import io.pivotal.racquetandroid.MockRacquetRestService;
import io.pivotal.racquetandroid.RacquetApplication;
import io.pivotal.racquetandroid.RacquetRestService;
import io.pivotal.racquetandroid.TestApplicationComponent;
import io.pivotal.racquetandroid.model.Club;
import io.pivotal.racquetandroid.util.ModelBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@Config(constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class ClubsFragmentTest {

    @Inject
    RacquetRestService restService;

    MockRacquetRestService mockRestService;

    private ClubsFragment fragment;

    @Before
    public void setup() {
        fragment = ClubsFragment.newInstance();
        ((TestApplicationComponent) RacquetApplication.getApplication().getApplicationComponent()).inject(this);
        RacquetApplication.getApplication().getApplicationComponent().inject(fragment);
        mockRestService = (MockRacquetRestService) restService;
    }

    @Test
    public void onViewCreated_shouldPopulateAdapter() {
        List<Club> clubs = ModelBuilder.getClubs(4, "Club Name");
        mockRestService.addResponse(clubs, true);
        SupportFragmentTestUtil.startFragment(fragment);
        assertThat(fragment.adapter.getItemCount()).isEqualTo(4);
    }

    @Test
    public void pullToRefresh_refetchesClubs() {
        SupportFragmentTestUtil.startFragment(fragment);
        assertThat(fragment.adapter.getItemCount()).isEqualTo(0);

        List<Club> clubs = ModelBuilder.getClubs(4, "Club Name");
        mockRestService.addResponse(clubs, true);
        fragment.onRefresh();

        assertThat(fragment.adapter.getItemCount()).isEqualTo(4);
    }
}