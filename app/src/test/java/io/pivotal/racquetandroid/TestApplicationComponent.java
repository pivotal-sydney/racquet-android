package io.pivotal.racquetandroid;

import javax.inject.Singleton;

import dagger.Component;
import io.pivotal.racquetandroid.fragment.ClubsFragmentTest;

@Singleton
@Component(modules = {TestApplicationModule.class})
public interface TestApplicationComponent extends ApplicationComponent {
    void inject(ClubsFragmentTest fragment);
}
