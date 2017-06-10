package com.taurus.carpooling.core;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenter<V extends BaseView> extends MvpBasePresenter<V> {

    @Inject
    Navigator navigator;

    protected CompositeDisposable compositeDisposable;

    public BasePresenter(){
        compositeDisposable = new CompositeDisposable();
    }

    public Navigator getNavigator() {
        return navigator;
    }

    public void clearCompositeDisposable() {

        if (compositeDisposable != null) {

            compositeDisposable.clear();
        }
    }

}
