package io.pivotal.racquetandroid.view;

import android.content.res.Resources;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.res.Attribute;
import org.robolectric.res.ResourceLoader;
import org.robolectric.shadows.RoboAttributeSet;

import javax.inject.Inject;

import io.pivotal.racquetandroid.BuildConfig;
import io.pivotal.racquetandroid.RacquetApplication;
import io.pivotal.racquetandroid.TestApplicationComponent;

import static java.util.Arrays.asList;
import static org.assertj.android.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.robolectric.Shadows.shadowOf;

@Config(constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class ProfileViewTest {

    @Inject
    Picasso picasso;

    @Inject
    RequestCreator requestCreator;

    @Test
    public void settingAttrs_createsViewWithAttrs() {
        ((TestApplicationComponent)RacquetApplication.getApplication().getApplicationComponent()).inject(this);
        Resources resources = RuntimeEnvironment.application.getResources();
        ResourceLoader resourceLoader = shadowOf(resources).getResourceLoader();

        ProfileView view = new ProfileView(RuntimeEnvironment.application,
                new RoboAttributeSet(asList(
                        new Attribute("io.pivotal.racquetandroid:attr/profile_name", "Profile Name", "io.pivotal.racquetandroid"),
                        new Attribute("io.pivotal.racquetandroid:attr/profile_image_src", "http://profile/url", "io.pivotal.racquetandroid")
                ), resourceLoader)
        );

        assertThat(view.profileNameView).hasText("Profile Name");
        verify(picasso).load("http://profile/url");
        verify(requestCreator).into(view.profileImageView);
    }

    @Test
    public void settingNameUrl_updatesView() {
        ((TestApplicationComponent)RacquetApplication.getApplication().getApplicationComponent()).inject(this);

        ProfileView view = new ProfileView(RuntimeEnvironment.application);
        view.setProfileName("Profile Name");
        view.setProfileImageUrl("http://profile/url");

        assertThat(view.profileNameView).hasText("Profile Name");
        verify(picasso).load("http://profile/url");
        verify(requestCreator).into(view.profileImageView);
    }

}