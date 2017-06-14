package com.taurus.carpooling.splash;

import android.arch.lifecycle.LifecycleObserver;

import com.taurus.carpooling.core.BasePresenter;
import com.taurus.carpooling.core.injection.Injector;
import com.taurus.carpooling.database.model.PlaceMarkerDatabaseModel;
import com.taurus.carpooling.repository.PlaceMarker;
import com.taurus.carpooling.repository.PlaceMarkerRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SplashPresenter extends BasePresenter<SplashView> implements LifecycleObserver {

    @Inject
    PlaceMarkerRepository placeMarkerRepository;

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

    void onCardFeedsRequested(boolean onlineRequired) {


        placeMarkerRepository.loadPlaceMarker(onlineRequired)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(PlaceMarkerDatabaseModel::createList)
                .subscribe(this::handleResponse, this::handleError);


    }

    private void handleResponse(List<PlaceMarker> placeMarkers) {

        onProgressBarHide();
        onPlaceMarkerActivityRequested(placeMarkers);

    }

    private void onPlaceMarkerActivityRequested(List<PlaceMarker> placeMarkers) {
        getNavigator().toPlaceMarkerActivity(placeMarkers).clearBackStack().navigate();
    }


    private void handleError(Throwable throwable) {

        onProgressBarHide();
        getView().showError(throwable.getMessage());

    }

}