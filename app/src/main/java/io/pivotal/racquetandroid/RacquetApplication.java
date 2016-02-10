package io.pivotal.racquetandroid;

import android.app.Application;

public class RacquetApplication extends Application {

    protected ApplicationComponent applicationComponent;

    static RacquetApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        buildComponent();
        application = this;
    }

    protected void buildComponent() {
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule()).build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public static RacquetApplication getApplication() {
        return application;
    }
}
