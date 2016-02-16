package io.pivotal.racquetandroid.activity;

import android.content.Intent;

import com.squareup.otto.Bus;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenuItem;
import org.robolectric.util.ActivityController;

import javax.inject.Inject;

import io.pivotal.racquetandroid.BuildConfig;
import io.pivotal.racquetandroid.RacquetApplication;
import io.pivotal.racquetandroid.TestApplicationComponent;
import io.pivotal.racquetandroid.model.Club;
import io.pivotal.racquetandroid.util.ModelBuilder;

import static org.assertj.android.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

@Config(constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class ClubActivityTest {

    private ClubActivity activity;

    @Inject
    Bus bus;

    @Before
    public void setup() {
        ((TestApplicationComponent) RacquetApplication.getApplication().getApplicationComponent()).inject(this);
        Club club = ModelBuilder.getClub(1, "My Club");
        Intent intent = ClubActivity.getIntent(RuntimeEnvironment.application, club);
        ActivityController<ClubActivity> activityController = ActivityController.of(Robolectric.getShadowsAdapter(), ClubActivity.class);
        activity = activityController.withIntent(intent).setup().get();
    }

    @Test
    public void startActivity_setsTitleAsClubName() {
        assertThat(activity.toolbar.getTitle()).isEqualTo("My Club");
    }

    @Test
    public void hasFeedAndLeaderboardTabs() {
        assertThat(activity.tabLayout.getTabCount()).isEqualTo(2);
        //noinspection ConstantConditions
        assertThat(activity.tabLayout.getTabAt(0).getText()).isEqualTo("Feed");
        //noinspection ConstantConditions
        assertThat(activity.tabLayout.getTabAt(1).getText()).isEqualTo("Leaderboard");
    }

    @Test
    public void onToolbarNavigationClick_shouldFinishActivity() {
        activity.onOptionsItemSelected(new RoboMenuItem(android.R.id.home));

        assertThat(activity).isFinishing();
    }

    @Test
    public void onClickFAB_shouldShowRecordMatchView() throws Exception {
        assertThat(activity.recordMatchView).isInvisible();
        activity.button.performClick();
        assertThat(activity.recordMatchView).isVisible();
        assertThat(activity.button).isGone();
    }

    @Test
    public void onDismissRecordMatchEvent_shouldHideRecordMatchView() {
        activity.animatorListenerAdapter.onAnimationEnd(null);
        assertThat(activity.recordMatchView).isInvisible();
        assertThat(activity.button).isVisible();
    }
}