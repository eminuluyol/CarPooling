package com.taurus.carpooling.placemarker.listener;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.taurus.carpooling.placemarker.adapter.PlaceMarkerUIModel;

import java.util.List;

public abstract class MyOnMapReadyCallback implements OnMapReadyCallback {

    List<PlaceMarkerUIModel> placeMarkers;

    public MyOnMapReadyCallback(List<PlaceMarkerUIModel> placeMarkers){
        this.placeMarkers = placeMarkers;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.onMapReadyShops(googleMap, placeMarkers);
    }

    public abstract void onMapReadyShops(GoogleMap googleMap,List<PlaceMarkerUIModel> placeMarkers);
}
