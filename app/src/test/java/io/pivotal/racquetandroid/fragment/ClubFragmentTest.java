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

import static org.assertj.core.api.Assertions.assertThat;

@Config(constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class ClubFragmentTest {

    @Inject
    RacquetRestService restService;

    MockRacquetRestService mockRestService;

    private ClubFragment fragment;

    @Before
    public void setup() {
        fragment = ClubFragment.newInstance();
        ((TestApplicationComponent) RacquetApplication.getApplication().getApplicationComponent()).inject(this);
        RacquetApplication.getApplication().getApplicationComponent().inject(fragment);
        mockRestService = (MockRacquetRestService) restService;
    }

    @Test
    public void onViewCreated_shouldPopulateAdapter() {
        List<Club> clubs = new ArrayList<>();
        Club club = new Club();
        club.setName("Pivotal Sydney");
        clubs.add(club);
        club = new Club();
        club.setName("Pivotal NY");
        clubs.add(club);
        club = new Club();
        club.setName("Pivotal Toronto");
        clubs.add(club);
        club = new Club();
        club.setName("Pivotal San Francisco");
        clubs.add(club);

        mockRestService.addResponse(clubs, true);

        SupportFragmentTestUtil.startFragment(fragment);

        assertThat(fragment.adapter.getItemCount()).isEqualTo(4);
    }
}