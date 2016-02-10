package io.pivotal.racquetandroid;

public class TestRacquetApplication extends RacquetApplication {
    @Override
    protected void buildComponent() {
        applicationComponent = DaggerTestApplicationComponent.builder().testApplicationModule(new TestApplicationModule()).build();
    }

}
