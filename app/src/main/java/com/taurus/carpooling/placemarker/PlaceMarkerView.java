package com.taurus.carpooling.placemarker;

import com.google.android.gms.common.GoogleApiAvailability;
import com.taurus.carpooling.core.BaseView;

public interface PlaceMarkerView extends BaseView {

    void showEmptyView();

    void hideEmptyView();

    void showInformationDialog(GoogleApiAvailability apiAvailability, int isAvailable);

    void showGoogleServiceError();

    void showMap();
}
