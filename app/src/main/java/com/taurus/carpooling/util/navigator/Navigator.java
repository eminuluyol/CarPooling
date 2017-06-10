package com.taurus.carpooling.util.navigator;

import android.app.Activity;

import com.taurus.carpooling.core.injection.ActivityScope;
import com.taurus.carpooling.placemarker.PlaceMarkerActivity;
import com.taurus.carpooling.repository.model.PlaceMarkerDatabaseModel;

import java.util.List;

@ActivityScope
public class Navigator {

    private final Activity activity;

    public Navigator(Activity activity) {
        this.activity = activity;
    }

    public Navigation toPlaceMarkerActivity(List<PlaceMarkerDatabaseModel> placeMarkers) {
        return new Navigation(activity, PlaceMarkerActivity.newIntent(activity, placeMarkers));
    }
}
