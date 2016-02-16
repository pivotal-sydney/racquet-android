package io.pivotal.racquetandroid.fragment;

import com.squareup.otto.Bus;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import javax.inject.Inject;

import io.pivotal.racquetandroid.BuildConfig;
import io.pivotal.racquetandroid.MockRacquetRestService;
import io.pivotal.racquetandroid.RacquetApplication;
import io.pivotal.racquetandroid.RacquetRestService;
import io.pivotal.racquetandroid.TestApplicationComponent;
import io.pivotal.racquetandroid.event.DismissRecordMatchEvent;
import io.pivotal.racquetandroid.event.MatchUpdatedEvent;
import io.pivotal.racquetandroid.model.Club;
import io.pivotal.racquetandroid.model.response.Matches;
import io.pivotal.racquetandroid.util.ModelBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@Config(constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class FeedFragmentTest {

    @Inject
    RacquetRestService restService;

    @Inject
    Bus bus;

    MockRacquetRestService mockRestService;

    private FeedFragment fragment;

    @Before
    public void setup() {
        Club club = ModelBuilder.getClub(1, "pivotal-sydney");
        fragment = FeedFragment.newInstance(club);
        ((TestApplicationComponent) RacquetApplication.getApplication().getApplicationComponent()).inject(this);
        mockRestService = (MockRacquetRestService) restService;
    }

    @Test
    public void onViewCreated_shouldFetchMatches() {
        Matches matches = ModelBuilder.getMatches(5, "p1", "p2");
        mockRestService.addResponse(matches, true);
        SupportFragmentTestUtil.startFragment(fragment);

        assertThat(fragment.adapter.getItemCount()).isEqualTo(5);
    }

    @Test
    public void pullToRefresh_shouldRepopulateResults() {
        SupportFragmentTestUtil.startFragment(fragment);

        assertThat(fragment.adapter.getItemCount()).isEqualTo(0);

        Matches matches = ModelBuilder.getMatches(5, "p1", "p2");
        mockRestService.addResponse(matches, true);

        fragment.onRefresh();

        assertThat(fragment.adapter.getItemCount()).isEqualTo(5);
    }

    @Test
    public void onMatchUpdatedEvent_shouldInsertMatch() throws Exception {
        SupportFragmentTestUtil.startFragment(fragment);

        bus.post(new MatchUpdatedEvent(ModelBuilder.getMatch("winner", "loser")));

        assertThat(fragment.adapter.getItemCount()).isEqualTo(1);
        bus.post(new DismissRecordMatchEvent());
        assertThat(fragment.adapter.getItemCount()).isEqualTo(1);
    }
}