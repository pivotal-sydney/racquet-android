package io.pivotal.racquetandroid;

import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@Module
public class TestApplicationModule {

    @Provides
    @Singleton
    public RacquetRestService providesRacquetRestService() {
        return spy(new MockRacquetRestService());
    }

    @Provides
    @Singleton
    public RequestCreator providesRequestCreator() {
        RequestCreator requestCreator = mock(RequestCreator.class);
        when(requestCreator.placeholder(anyInt())).thenReturn(requestCreator);
        when(requestCreator.error(anyInt())).thenReturn(requestCreator);
        when(requestCreator.transform(any(Transformation.class))).thenReturn(requestCreator);
        return requestCreator;
    }

    @Provides
    @Singleton
    public Picasso providesPicasso(RequestCreator requestCreator) {
        Picasso picasso = mock(Picasso.class);
        when(picasso.load(anyString())).thenReturn(requestCreator);
        return picasso;
    }

    @Provides
    @Singleton
    public Bus providesBus() {
        return spy(new Bus());
    }

}