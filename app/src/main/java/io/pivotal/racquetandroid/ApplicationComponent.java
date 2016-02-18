package io.pivotal.racquetandroid;

import javax.inject.Singleton;

import dagger.Component;
import io.pivotal.racquetandroid.activity.ClubActivity;
import io.pivotal.racquetandroid.adapter.FilteredPlayerAdapter;
import io.pivotal.racquetandroid.adapter.LeaderboardAdapter;
import io.pivotal.racquetandroid.fragment.ClubsFragment;
import io.pivotal.racquetandroid.fragment.FeedFragment;
import io.pivotal.racquetandroid.fragment.LeaderboardFragment;
import io.pivotal.racquetandroid.view.ProfileView;
import io.pivotal.racquetandroid.view.RecordMatchView;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    void inject(ClubsFragment object);
    void inject(FeedFragment object);
    void inject(ProfileView profileView);
    void inject(RecordMatchView recordMatchView);
    void inject(ClubActivity clubActivity);
    void inject(LeaderboardAdapter leaderboardAdapter);
    void inject(LeaderboardFragment leaderboardFragment);
    void inject(FilteredPlayerAdapter filteredPlayerAdapter);
}