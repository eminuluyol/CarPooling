package com.taurus.carpooling.core.injection;

import android.app.Application;

import com.taurus.carpooling.network.retrofit.RetrofitCarPoolingApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    public RetrofitCarPoolingApi provideCarPoolingApi() {
        return new RetrofitCarPoolingApi();
    }

}
