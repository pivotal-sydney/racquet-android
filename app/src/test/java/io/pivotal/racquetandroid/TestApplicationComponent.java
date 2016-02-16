package io.pivotal.racquetandroid;

import javax.inject.Singleton;

import dagger.Component;
import io.pivotal.racquetandroid.activity.ClubActivityTest;
import io.pivotal.racquetandroid.adapter.ClubsAdapterTest;
import io.pivotal.racquetandroid.adapter.MatchesAdapterTest;
import io.pivotal.racquetandroid.fragment.ClubsFragmentTest;
import io.pivotal.racquetandroid.fragment.FeedFragmentTest;
import io.pivotal.racquetandroid.view.ProfileViewTest;
import io.pivotal.racquetandroid.view.RecordMatchViewTest;

@Singleton
@Component(modules = {TestApplicationModule.class})
public interface TestApplicationComponent extends ApplicationComponent {
    void inject(ClubsFragmentTest fragment);
    void inject(FeedFragmentTest fragment);
    void inject(MatchesAdapterTest adapter);
    void inject(ClubsAdapterTest adapter);
    void inject(ProfileViewTest profileViewTest);
    void inject(RecordMatchViewTest recordMatchViewTest);
    void inject(ClubActivityTest clubActivityTest);
}
