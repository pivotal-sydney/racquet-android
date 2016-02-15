package io.pivotal.racquetandroid.activity;

import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenuItem;
import org.robolectric.util.ActivityController;

import io.pivotal.racquetandroid.BuildConfig;
import io.pivotal.racquetandroid.model.Club;
import io.pivotal.racquetandroid.util.ModelBuilder;

import static org.assertj.android.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

@Config(constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class ClubActivityTest {

    private ClubActivity activity;

    @Before
    public void setup() {
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
}