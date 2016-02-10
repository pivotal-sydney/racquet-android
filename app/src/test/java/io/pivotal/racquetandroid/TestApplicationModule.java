package io.pivotal.racquetandroid;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TestApplicationModule {

    @Provides
    @Singleton
    public RacquetRestService providesRacquetRestService() {
        return new MockRacquetRestService();
    }
}