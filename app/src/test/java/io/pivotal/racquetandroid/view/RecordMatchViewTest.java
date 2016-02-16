package io.pivotal.racquetandroid.view;

import android.widget.TextView;

import com.squareup.otto.Bus;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import javax.inject.Inject;

import io.pivotal.racquetandroid.BuildConfig;
import io.pivotal.racquetandroid.MockRacquetRestService;
import io.pivotal.racquetandroid.R;
import io.pivotal.racquetandroid.RacquetApplication;
import io.pivotal.racquetandroid.RacquetRestService;
import io.pivotal.racquetandroid.TestApplicationComponent;
import io.pivotal.racquetandroid.event.DismissRecordMatchEvent;
import io.pivotal.racquetandroid.event.MatchUpdatedEvent;
import io.pivotal.racquetandroid.model.request.MatchResult;
import io.pivotal.racquetandroid.model.response.Match;
import io.pivotal.racquetandroid.util.ModelBuilder;

import static org.assertj.android.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@Config(constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class RecordMatchViewTest {

    private RecordMatchView view;

    @Inject
    RacquetRestService racquetRestService;

    @Inject
    Bus bus;

    MockRacquetRestService mockRestService;

    @Before
    public void setup() {
        ((TestApplicationComponent) RacquetApplication.getApplication().getApplicationComponent()).inject(this);
        view = new RecordMatchView(RuntimeEnvironment.application);
        mockRestService = (MockRacquetRestService) racquetRestService;
        view.setClub(ModelBuilder.getClub(1, "name"));
    }

    @Test
    public void hasViews() {
        assertThat((TextView)view.findViewById(R.id.boom)).hasText("Boom!");
        assertThat((TextView)view.findViewById(R.id.record_text)).hasText("Record a new match result");
        assertThat((TextView)view.findViewById(R.id.defeated)).hasText("defeated");

        assertThat(view.winner).hasHint("\\@winner");
        assertThat(view.loser).hasHint("\\@loser");
        assertThat(view.submit).hasText("Go");
    }


    @Test
    public void onSubmit_shouldPostMatchResultToRacquetRestService() {
        Match match = ModelBuilder.getMatch("p1", "p2");
        mockRestService.addResponse(match, true);
        view.winner.setText("winner");
        view.loser.setText("loser");

        view.submit.performClick();

        MatchResult matchResult = mockRestService.getLastMatchResult().getMatch();
        Assertions.assertThat(matchResult.getWinner()).isEqualTo("winner");
        Assertions.assertThat(matchResult.getLoser()).isEqualTo("loser");

        ArgumentCaptor<MatchUpdatedEvent> captor = ArgumentCaptor.forClass(MatchUpdatedEvent.class);
        verify(bus, atLeastOnce()).post(captor.capture());

        assertThat(captor.getAllValues().get(0).getMatch()).isEqualTo(match);
    }

    @Test
    public void clickingButtons_shouldPostDismissEvent() {
        view.submit.performClick();
        view.cancel.performClick();

        ArgumentCaptor<DismissRecordMatchEvent> captor = ArgumentCaptor.forClass(DismissRecordMatchEvent.class);
        verify(bus, atLeastOnce()).post(captor.capture());

        assertThat(captor.getAllValues().get(0)).isInstanceOf(DismissRecordMatchEvent.class);
        assertThat(captor.getAllValues().get(2)).isInstanceOf(DismissRecordMatchEvent.class);
    }
}