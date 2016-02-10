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
        fragment = ClubFragment.newInstance();
        ((TestApplicationComponent) RacquetApplication.getApplication().getApplicationComponent()).inject(this);
        SupportFragmentTestUtil.startFragment(fragment);
        mockRestService = (MockRacquetRestService) restService;
    }

    @Test
    public void onViewCreated_shouldShowMatchInput() {
        assertThat(fragment.loser).hasHint("\\@loser");
        assertThat(fragment.winner).hasHint("\\@winner");
        assertThat(shadowOf(fragment.submit).getImageResourceId()).isEqualTo(R.drawable.ic_done_black_24dp);
    }
}