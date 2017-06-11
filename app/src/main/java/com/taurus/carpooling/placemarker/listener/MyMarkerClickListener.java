package com.taurus.carpooling.placemarker.listener;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class MyMarkerClickListener implements GoogleMap.OnMarkerClickListener {

    private Marker lastClicked;

    @Override
    public boolean onMarkerClick(Marker marker) {

        if (lastClicked != null && lastClicked.equals(marker)) {
            lastClicked = null;
            marker.hideInfoWindow();
            return true;
        } else {
            lastClicked = marker;
            return false;
        }

    }
}
