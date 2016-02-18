package io.pivotal.racquetandroid.model.response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import io.pivotal.racquetandroid.BuildConfig;

import static org.assertj.core.api.Assertions.assertThat;

@Config(constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class PlayerTest {

    @Test
    public void playerHash_shouldHashTwitterHandle() {
        Player player = new Player();
        player.setTwitterHandle("player");

        Player player2 = new Player();
        player2.setTwitterHandle("player");

        assertThat(player.hashCode()).isEqualTo(player2.hashCode());
        assertThat(player).isEqualTo(player2);
    }

}