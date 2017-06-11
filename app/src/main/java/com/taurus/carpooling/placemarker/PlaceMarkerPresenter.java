package com.taurus.carpooling.placemarker;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.taurus.carpooling.core.BasePresenter;
import com.taurus.carpooling.core.injection.Injector;

public class PlaceMarkerPresenter extends BasePresenter<PlaceMarkerView> {

    PlaceMarkerPresenter() {
        Injector.getInstance().getActivityComponent().inject(this);
    }

    void showEmptyView() {

        if(isViewAttached()) {
            getView().showEmptyView();
        }
    }

    void hideEmptyView() {

        if(isViewAttached()) {
            getView().hideEmptyView();
        }
    }

    void checkGoogleServiceAvailability() {

        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int isAvailable = apiAvailability.isGooglePlayServicesAvailable(getApplication());

        if(isAvailable == ConnectionResult.SUCCESS){

           getView().showMap();

        } else if(apiAvailability.isUserResolvableError(isAvailable)) {

            if(isViewAttached()) {
                getView().showInformationDialog(apiAvailability, isAvailable);
            }

        }else {

            if(isViewAttached()) {
                getView().showGoogleServiceError();
            }

        }
    }
}
