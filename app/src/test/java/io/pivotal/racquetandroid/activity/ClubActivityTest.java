package io.pivotal.racquetandroid.activity;

import android.content.Intent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import io.pivotal.racquetandroid.BuildConfig;
import io.pivotal.racquetandroid.model.Club;
import io.pivotal.racquetandroid.util.ModelBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@Config(constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class ClubActivityTest {

    @Test
    public void startActivity_setsTitleAsClubName() {
        Club club = ModelBuilder.getClub(1, "My Club");
        Intent intent = ClubActivity.getIntent(RuntimeEnvironment.application, club);
        ActivityController<ClubActivity> activityController = ActivityController.of(Robolectric.getShadowsAdapter(), ClubActivity.class);
        ClubActivity activity = activityController.withIntent(intent).setup().get();
        assertThat(activity.getTitle()).isEqualTo("My Club");
    }
}