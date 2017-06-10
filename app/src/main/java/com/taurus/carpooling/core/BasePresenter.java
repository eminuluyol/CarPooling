package com.taurus.carpooling.core;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.taurus.carpooling.network.retrofit.RetrofitCarPoolingApi;
import com.taurus.carpooling.repository.CarPoolingDatabaseHandler;
import com.taurus.carpooling.util.navigator.Navigator;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenter<V extends BaseView> extends MvpBasePresenter<V> {

    @Inject
    Navigator navigator;

    @Inject
    RetrofitCarPoolingApi api;

    @Inject
    CarPoolingDatabaseHandler carPoolingDatabaseHandler;

    protected CompositeDisposable compositeDisposable;

    public BasePresenter(){
        compositeDisposable = new CompositeDisposable();
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

    public void clearCompositeDisposable() {

        if (compositeDisposable != null) {

            compositeDisposable.clear();
        }
    }

}
