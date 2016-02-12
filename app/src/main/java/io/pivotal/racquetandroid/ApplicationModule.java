package io.pivotal.racquetandroid;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public class ApplicationModule {

    private final Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public Retrofit.Builder providesRetrofitBuilder() {
        return new Retrofit.Builder().client(new OkHttpClient.Builder().build());
    }

    @Provides
    @Singleton
    public RacquetRestService providesRacquetRestService(Retrofit.Builder builder) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);

        return builder
                .baseUrl(BuildConfig.RACQUETIO_BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build()
                .create(RacquetRestService.class);
    }

    @Provides
    @Singleton
    public Picasso providesPicasso() {
        return Picasso.with(context);
    }
}
