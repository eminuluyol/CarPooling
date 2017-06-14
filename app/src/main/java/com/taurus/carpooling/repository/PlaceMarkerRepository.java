package com.taurus.carpooling.repository;

import com.taurus.carpooling.database.model.PlaceMarkerDatabaseModel;
import com.taurus.carpooling.repository.local.PlaceMarkerLocalDataSource;
import com.taurus.carpooling.repository.remote.PlaceMarkerRemoteDataSource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

@Singleton
public class PlaceMarkerRepository implements PlaceMarkerDataSource {

    private PlaceMarkerDataSource remoteDataSource;
    private PlaceMarkerDataSource localDataSource;

    @Inject
    public PlaceMarkerRepository(PlaceMarkerLocalDataSource localDataSource, PlaceMarkerRemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public Flowable<List<PlaceMarkerDatabaseModel>> loadPlaceMarker(boolean forceRemote) {

        if (forceRemote) {

            return remoteDataSource.loadPlaceMarker(true).doOnEach(notification -> {
                // Save new data to local data source
                List<PlaceMarkerDatabaseModel> list = notification.getValue();
                if (list != null && !list.isEmpty()) {
                    saveDataToLocal(list);
                }
            });

        } else {
            return localDataSource.loadPlaceMarker(false);
        }

    }

    @Override
    public void addPlaceMarker(PlaceMarkerDatabaseModel placeMarkerDatabaseModel) {

    }

    @Override
    public void clearData() {

    }

    // A helper method to save data in database after fetching new data from remote source.
    private void saveDataToLocal(List<PlaceMarkerDatabaseModel> placeMarkerDatabaseModels) {
        // Clear old data
        localDataSource.clearData();
        for (PlaceMarkerDatabaseModel placeMarkerDatabaseModel : placeMarkerDatabaseModels) {
            localDataSource.addPlaceMarker(placeMarkerDatabaseModel);
        }
    }

}
