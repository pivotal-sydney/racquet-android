package io.pivotal.racquetandroid.adapter;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

import javax.inject.Inject;

import io.pivotal.racquetandroid.BuildConfig;
import io.pivotal.racquetandroid.R;
import io.pivotal.racquetandroid.RacquetApplication;
import io.pivotal.racquetandroid.TestApplicationComponent;
import io.pivotal.racquetandroid.model.Leaderboard;
import io.pivotal.racquetandroid.model.Ranking;
import io.pivotal.racquetandroid.util.ModelBuilder;

import static org.assertj.android.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Config(constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class LeaderboardAdapterTest {

    private LeaderboardAdapter adapter;

    @Inject
    Picasso picasso;

    @Inject
    RequestCreator requestCreator;

    @Before
    public void setup() {
        List<Ranking> majors = ModelBuilder.getRankings(5);
        List<Ranking> minors = ModelBuilder.getRankings(10);
        Leaderboard leaderboard = ModelBuilder.getLeaderboard(majors, minors);
        adapter = new LeaderboardAdapter(leaderboard);
        ((TestApplicationComponent) RacquetApplication.getApplication().getApplicationComponent()).inject(this);
    }

    @Test
    public void onCreateAndBindsView_majorLeagueHeader() {
        LeaderboardAdapter.MajorLeagueHeaderViewHolder holder = (LeaderboardAdapter.MajorLeagueHeaderViewHolder) adapter.onCreateViewHolder(new LinearLayout(RuntimeEnvironment.application), LeaderboardAdapter.MAJOR_LEAGUE_HEADER);
        adapter.onBindViewHolder(holder, 0);

        assertThat((TextView)holder.itemView.findViewById(R.id.text)).hasText("Major Leagues");
        assertThat((TextView)holder.itemView.findViewById(R.id.major_description)).hasText("Where all the pros play");
    }

    @Test
    public void onCreateAndBindsView_majorLeagueItem() {
        LeaderboardAdapter.MajorLeagueItemViewHolder holder = (LeaderboardAdapter.MajorLeagueItemViewHolder) adapter.onCreateViewHolder(new LinearLayout(RuntimeEnvironment.application), LeaderboardAdapter.MAJOR_LEAGUE_ITEM);
        adapter.onBindViewHolder(holder, 5);

        assertThat(holder.rank).hasText("5");
        assertThat(holder.name).hasText("name5");
        assertThat(holder.wins).hasText("5 WINS");
        assertThat(holder.losses).hasText("10 LOSSES");
        assertThat(holder.percentage).hasText(".500");
        assertThat(holder.points).hasText("500 pts");
        verify(picasso).load("http://profile/url/name5");
        verify(requestCreator).into(holder.profile);
    }

    @Test
    public void onCreateAndBind_minorLeague() {
        LeaderboardAdapter.MinorLeaguesViewHolder minorHolder = (LeaderboardAdapter.MinorLeaguesViewHolder) adapter.onCreateViewHolder(new LinearLayout(RuntimeEnvironment.application), LeaderboardAdapter.MINOR_LEAGUE_ITEMS);
        adapter.onBindViewHolder(minorHolder, 0);

        assertThat(minorHolder.list.getChildCount()).isEqualTo(9);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(picasso, times(8)).load(captor.capture());
        assertThat(captor.getAllValues().get(0)).isEqualTo("http://profile/url/name1");
        assertThat(captor.getAllValues().get(7)).isEqualTo("http://profile/url/name8");
        verify(requestCreator, times(8)).into(isA(ImageView.class));

        assertThat((TextView) minorHolder.itemView.findViewById(R.id.minor_description)).hasText("Where all the boys and girls play");
    }

    @Test
    public void whenMinorsHasFewerThan8_shouldOnlyDisplaySize() {
        List<Ranking> majors = ModelBuilder.getRankings(1);
        List<Ranking> minors = ModelBuilder.getRankings(1);
        Leaderboard leaderboard = ModelBuilder.getLeaderboard(majors, minors);
        adapter = new LeaderboardAdapter(leaderboard);
        LeaderboardAdapter.MinorLeaguesViewHolder minorHolder = (LeaderboardAdapter.MinorLeaguesViewHolder) adapter.onCreateViewHolder(new LinearLayout(RuntimeEnvironment.application), LeaderboardAdapter.MINOR_LEAGUE_ITEMS);
        adapter.onBindViewHolder(minorHolder, 0);
        assertThat(minorHolder.list.getChildCount()).isEqualTo(2);
    }

    @Test
    public void itemCount_matchesMajorsSize() {
        assertThat(adapter.getItemCount()).isEqualTo(7);
    }

    @Test
    public void getItemViewType() {
        assertThat(adapter.getItemViewType(0)).isEqualTo(LeaderboardAdapter.MAJOR_LEAGUE_HEADER);
        assertThat(adapter.getItemViewType(1)).isEqualTo(LeaderboardAdapter.MAJOR_LEAGUE_ITEM);
        assertThat(adapter.getItemViewType(5)).isEqualTo(LeaderboardAdapter.MAJOR_LEAGUE_ITEM);
        assertThat(adapter.getItemViewType(6)).isEqualTo(LeaderboardAdapter.MINOR_LEAGUE_ITEMS);
    }
}