package io.pivotal.racquetandroid;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public class ApplicationModule {

    @Provides
    @Singleton
    public Retrofit.Builder providesRetrofitBuilder() {
        return new Retrofit.Builder().client(new OkHttpClient.Builder().build());
    }

    @Provides
    @Singleton
    public RacquetRestService providesRacquetRestService(Retrofit.Builder builder) {
        return builder
                .baseUrl(BuildConfig.RACQUETIO_BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(RacquetRestService.class);
    }
}
