package io.pivotal.racquetandroid.adapter;

import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import javax.inject.Inject;

import io.pivotal.racquetandroid.BuildConfig;
import io.pivotal.racquetandroid.RacquetApplication;
import io.pivotal.racquetandroid.TestApplicationComponent;
import io.pivotal.racquetandroid.model.response.Matches;
import io.pivotal.racquetandroid.util.ModelBuilder;

import static org.assertj.android.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Config(constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class MatchesAdapterTest {

    @Inject
    Picasso picasso;

    @Inject
    RequestCreator requestCreator;

    private MatchesAdapter adapter;

    @Before
    public void setup() {
        ((TestApplicationComponent) RacquetApplication.getApplication().getApplicationComponent()).inject(this);
        Matches matches = ModelBuilder.getMatches(5, "player1", "player2");
        adapter = new MatchesAdapter(matches);
    }

    @Test
    public void adapter_returnsCorrectSize() {
        assertThat(adapter.getItemCount()).isEqualTo(5);
    }

    @Test
    public void adapter_onCreateAndBind_bindsData() {
        MatchesAdapter.MatchesViewHolder holder = adapter.onCreateViewHolder(new LinearLayout(RuntimeEnvironment.application), 0);

        adapter.onBindViewHolder(holder, 1);
        assertThat(holder.winner.getProfileName()).hasText("player2");
        assertThat(holder.loser.getProfileName()).hasText("player1");
        assertThat(holder.defeated).hasText("def");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(picasso, times(2)).load(captor.capture());

        assertThat(captor.getAllValues().get(0)).isEqualTo("http://profile/url/player2");
        assertThat(captor.getAllValues().get(1)).isEqualTo("http://profile/url/player1");

        ArgumentCaptor<ImageView> imageCaptor = ArgumentCaptor.forClass(ImageView.class);
        verify(requestCreator, times(2)).into(imageCaptor.capture());

        assertThat(imageCaptor.getAllValues().get(0)).isEqualTo(holder.winner.getProfileImageView());
        assertThat(imageCaptor.getAllValues().get(1)).isEqualTo(holder.loser.getProfileImageView());
    }
}