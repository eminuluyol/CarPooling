package com.taurus.carpooling.repository.remote;

import com.taurus.carpooling.database.model.PlaceMarkerDatabaseModel;
import com.taurus.carpooling.network.CarPoolingApi;
import com.taurus.carpooling.network.model.BaseRequest;
import com.taurus.carpooling.network.model.placemarker.PlaceMarksWrapper;
import com.taurus.carpooling.repository.PlaceMarkerDataSource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

@Singleton
public class PlaceMarkerRemoteDataSource implements PlaceMarkerDataSource {

    private CarPoolingApi carPoolingApi;

    @Inject
    public PlaceMarkerRemoteDataSource(CarPoolingApi carPoolingApi) {
        this.carPoolingApi = carPoolingApi;
    }

    @Override
    public Flowable<List<PlaceMarkerDatabaseModel>> loadPlaceMarker(boolean forceRemote) {

        final BaseRequest request = new BaseRequest();

        return carPoolingApi.getCarFeeds(request).map(PlaceMarksWrapper::createList);
    }

    @Override
    public void addPlaceMarker(PlaceMarkerDatabaseModel placeMarkerDatabaseModel) {
        //Currently, we do not need this for remote source.
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public void clearData() {
        //Currently, we do not need this for remote source.
        throw new UnsupportedOperationException("Unsupported operation");
    }
}
