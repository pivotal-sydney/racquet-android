package io.pivotal.racquetandroid.view;

import android.content.res.Resources;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.res.Attribute;
import org.robolectric.res.ResourceLoader;
import org.robolectric.shadows.RoboAttributeSet;

import java.util.Collections;

import io.pivotal.racquetandroid.BuildConfig;

import static org.assertj.android.api.Assertions.assertThat;
import static org.robolectric.Shadows.shadowOf;

@Config(constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class DividerTextViewTest {

    @Test
    public void settingAttrs_createsViewWithAttrs() {
        Resources resources = RuntimeEnvironment.application.getResources();
        ResourceLoader resourceLoader = shadowOf(resources).getResourceLoader();

        DividerTextView view = new DividerTextView(RuntimeEnvironment.application,
                new RoboAttributeSet(Collections.singletonList(
                    new Attribute("io.pivotal.racquetandroid:attr/text", "Some Text", "io.pivotal.racquetandroid")
                ), resourceLoader)
        );
        assertThat(view.textView).hasText("Some Text");

        view.setText("Some Other Text");
        assertThat(view.textView).hasText("Some Other Text");
    }
}