package com.taurus.carpooling.placemarker.listener;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

public class MyMarkerClickListener implements GoogleMap.OnMarkerClickListener {

    private Marker lastClicked;
    private ArrayList<Marker> myMarkers;

    public MyMarkerClickListener(ArrayList<Marker> myMarkers) {
        this.myMarkers = myMarkers;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        if (lastClicked != null && lastClicked.equals(marker)) {
            lastClicked = null;
            marker.hideInfoWindow();

            for (Marker m : myMarkers) {
                m.setVisible(true);
            }

            return true;
        } else {
            lastClicked = marker;

            for (Marker m : myMarkers) {

                if (!m.equals(lastClicked)) {
                    m.setVisible(false);
                }

            }

            return false;
        }

    }
}
