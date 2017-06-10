package com.taurus.carpooling.splash;

import android.util.Log;

import com.taurus.carpooling.core.BasePresenter;
import com.taurus.carpooling.core.injection.Injector;
import com.taurus.carpooling.network.model.BaseRequest;
import com.taurus.carpooling.network.model.placemarker.PlaceMarksWrapper;
import com.taurus.carpooling.network.model.placemarker.Placemark;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SplashPresenter extends BasePresenter<SplashView> {

    SplashPresenter() {
        Injector.getInstance().getActivityComponent().inject(this);
    }

    void onProgressBarShow() {

        if(isViewAttached()) {

            getView().showLoading();
        }
    }

    void onProgressBarHide() {

        if(isViewAttached()) {

            getView().hideLoading();
        }
    }

    void onCardFeedsRequested() {

        final BaseRequest request = new BaseRequest();

        compositeDisposable.add(getApi().getCarFeeds(request)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError));

    }

    private void handleResponse(PlaceMarksWrapper placeMarksWrappers) {

        List<Placemark> wrapper = placeMarksWrappers.getPlacemarks();

        Log.i("Size", wrapper.size() + "");



    }

    private void handleError(Throwable throwable) {


    }

}