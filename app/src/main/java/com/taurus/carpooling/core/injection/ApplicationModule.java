package com.taurus.carpooling.core.injection;

import android.app.Application;

import com.taurus.carpooling.network.retrofit.RetrofitCarPoolingApi;
import com.taurus.carpooling.repository.CarPoolingDatabaseHandler;
import com.taurus.carpooling.repository.PlaceMarkerRepository;
import com.taurus.carpooling.repository.local.PlaceMarkerLocalDataSource;
import com.taurus.carpooling.repository.remote.PlaceMarkerRemoteDataSource;
import com.taurus.carpooling.util.SharedPreferenceHelper;

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

    @Provides
    @Singleton
    public CarPoolingDatabaseHandler provideCarPoolingDatabaseHandler() {
        return new CarPoolingDatabaseHandler(application);
    }

    @Provides
    @Singleton
    public SharedPreferenceHelper provideSharedPreferenceHelper() {
        return new SharedPreferenceHelper(application);
    }

    @Provides
    @Singleton
    public PlaceMarkerLocalDataSource provideLocalDataSource() {
        return new PlaceMarkerLocalDataSource(application);
    }

    @Provides
    @Singleton
    public PlaceMarkerRemoteDataSource provideRemoteDataSource(RetrofitCarPoolingApi carPoolingApi) {
        return new PlaceMarkerRemoteDataSource(carPoolingApi);
    }

    @Provides
    @Singleton
    public PlaceMarkerRepository providePlaceMarkerRepository(PlaceMarkerLocalDataSource local,
                                                              PlaceMarkerRemoteDataSource remote) {
        return new PlaceMarkerRepository(local, remote);
    }

}
