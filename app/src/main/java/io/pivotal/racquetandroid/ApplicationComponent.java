package io.pivotal.racquetandroid;

import javax.inject.Singleton;

import dagger.Component;
import io.pivotal.racquetandroid.fragment.ClubsFragment;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    void inject(ClubsFragment object);
}
