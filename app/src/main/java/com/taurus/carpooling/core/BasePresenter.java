package com.taurus.carpooling.core;

import android.app.Application;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.taurus.carpooling.network.retrofit.RetrofitCarPoolingApi;
import com.taurus.carpooling.repository.CarPoolingDatabaseHandler;
import com.taurus.carpooling.repository.PlaceMarkerRepository;
import com.taurus.carpooling.util.SharedPreferenceHelper;
import com.taurus.carpooling.util.navigator.Navigator;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenter<V extends BaseView> extends MvpBasePresenter<V> {

    @Inject
    Application application;

    @Inject
    Navigator navigator;

    @Inject
    RetrofitCarPoolingApi api;

    @Inject
    CarPoolingDatabaseHandler carPoolingDatabaseHandler;

    @Inject
    SharedPreferenceHelper sharedPreference;

    @Inject
    PlaceMarkerRepository placeMarkerRepository;

    protected CompositeDisposable compositeDisposable;

    public BasePresenter(){
        compositeDisposable = new CompositeDisposable();
    }

    public Application getApplication() {
        return application;
    }

    public Navigator getNavigator() {
        return navigator;
    }

    public RetrofitCarPoolingApi getApi() {
        return api;
    }

    public CarPoolingDatabaseHandler getDatabaseHandler() {
        return carPoolingDatabaseHandler;
    }

    public SharedPreferenceHelper getSharedPreferenceHelper() {
        return sharedPreference;
    }

    public PlaceMarkerRepository getPlaceMarkerRepository() {
        return placeMarkerRepository;
    }

    public void clearCompositeDisposable() {

        if (compositeDisposable != null) {

            compositeDisposable.clear();
        }
    }

}
