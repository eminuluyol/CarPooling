package com.taurus.carpooling.repository;

import com.taurus.carpooling.database.model.PlaceMarkerDatabaseModel;

import java.util.List;

import io.reactivex.Flowable;

public interface PlaceMarkerDataSource {

    Flowable<List<PlaceMarkerDatabaseModel>> loadPlaceMarker(boolean forceRemote);

    void addPlaceMarker(PlaceMarkerDatabaseModel placeMarkerDatabaseModel);

    void clearData();

}
