package io.pivotal.racquetandroid.fragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import javax.inject.Inject;

import io.pivotal.racquetandroid.BuildConfig;
import io.pivotal.racquetandroid.MockRacquetRestService;
import io.pivotal.racquetandroid.R;
import io.pivotal.racquetandroid.RacquetApplication;
import io.pivotal.racquetandroid.RacquetRestService;
import io.pivotal.racquetandroid.TestApplicationComponent;
import io.pivotal.racquetandroid.model.Club;
import io.pivotal.racquetandroid.model.request.MatchResult;
import io.pivotal.racquetandroid.model.response.Matches;
import io.pivotal.racquetandroid.util.ModelBuilder;

import static org.assertj.android.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.robolectric.Shadows.shadowOf;

@Config(constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class ClubFragmentTest {

    @Inject
    RacquetRestService restService;

    MockRacquetRestService mockRestService;

    private ClubFragment fragment;

    @Before
    public void setup() {
        Club club = ModelBuilder.getClub(1, "pivotal-sydney");
        fragment = ClubFragment.newInstance(club);
        ((TestApplicationComponent) RacquetApplication.getApplication().getApplicationComponent()).inject(this);
        mockRestService = (MockRacquetRestService) restService;
    }

    @Test
    public void onViewCreated_shouldShowMatchInput() {
        SupportFragmentTestUtil.startFragment(fragment);
        assertThat(fragment.loser).hasHint("\\@loser");
        assertThat(fragment.winner).hasHint("\\@winner");
        assertThat(shadowOf(fragment.submit).getImageResourceId()).isEqualTo(R.drawable.ic_done_black_24dp);
    }

    @Test
    public void onSubmit_shouldPostMatchResultToRacquetRestService() {
        SupportFragmentTestUtil.startFragment(fragment);
        mockRestService.addResponse(null, true);
        fragment.winner.setText("winner");
        fragment.loser.setText("loser");

        fragment.submit.performClick();

        MatchResult matchResult = mockRestService.getLastMatchResult().getMatch();
        assertThat(matchResult.getWinner()).isEqualTo("winner");
        assertThat(matchResult.getLoser()).isEqualTo("loser");
    }

    @Test
    public void onViewCreated_shouldFetchMatches() {
        Matches matches = ModelBuilder.getMatches(5, "p1", "p2");
        mockRestService.addResponse(matches, true);
        SupportFragmentTestUtil.startFragment(fragment);

        assertThat(fragment.adapter.getItemCount()).isEqualTo(5);
    }
}