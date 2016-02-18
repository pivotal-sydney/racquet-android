package io.pivotal.racquetandroid.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import io.pivotal.racquetandroid.BuildConfig;
import io.pivotal.racquetandroid.R;
import io.pivotal.racquetandroid.RacquetApplication;
import io.pivotal.racquetandroid.TestApplicationComponent;
import io.pivotal.racquetandroid.model.response.Player;
import io.pivotal.racquetandroid.util.ModelBuilder;

import static org.assertj.android.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@Config(constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class FilteredPlayerAdapterTest {

    private FilteredPlayerAdapter adapter;

    @Inject
    Picasso picasso;

    @Inject
    RequestCreator requestCreator;
    private Player player1;
    private Player player2;

    @Before
    public void setup() {
        ((TestApplicationComponent)RacquetApplication.getApplication().getApplicationComponent()).inject(this);
        Set<Player> players = new HashSet<>();
        player1 = ModelBuilder.getPlayer("player1", 1);
        players.add(player1);
        player2 = ModelBuilder.getPlayer("player2", 1);
        players.add(player2);
        adapter = new FilteredPlayerAdapter(players);
    }

    @Test
    public void playerFilter_filtersByNameAndTwitterHandle() {
        assertThat(adapter.getCount()).isEqualTo(0);

        adapter.getFilter().filter("pLaYeR");
        assertThat(adapter.getCount()).isEqualTo(2);

        adapter.getFilter().filter("tWiTtEr");
        assertThat(adapter.getCount()).isEqualTo(2);

        adapter.getFilter().filter("1");
        assertThat(adapter.getItem(0)).isEqualTo(player1);
        assertThat(adapter.getCount()).isEqualTo(1);

        adapter.getFilter().filter("2");
        assertThat(adapter.getItem(0)).isEqualTo(player2);
        assertThat(adapter.getCount()).isEqualTo(1);

        adapter.getFilter().filter("@twit");
        assertThat(adapter.getCount()).isEqualTo(2);
    }

    @Test
    public void getView_bindsPlayerDataToView() {
        adapter.getFilter().filter("twitter_player1");
        View view = adapter.getView(0, null, new LinearLayout(RuntimeEnvironment.application));
        assertThat(((TextView) view.findViewById(R.id.name))).hasText("player1");
        assertThat(((TextView) view.findViewById(R.id.twitter_handle))).hasText("\\@twitter_player1");
        verify(picasso).load("http://profile/url/player1");
        verify(requestCreator).into(((ImageView)view.findViewById(R.id.profile)));
    }
}