package io.pivotal.racquetandroid;

import javax.inject.Singleton;

import dagger.Component;
import io.pivotal.racquetandroid.fragment.ClubsFragment;
import io.pivotal.racquetandroid.fragment.FeedFragment;
import io.pivotal.racquetandroid.view.ProfileView;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    void inject(ClubsFragment object);
    void inject(FeedFragment object);
    void inject(ProfileView profileView);
}
