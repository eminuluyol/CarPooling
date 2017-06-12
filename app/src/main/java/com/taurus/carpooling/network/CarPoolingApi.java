package com.taurus.carpooling.network;

import com.taurus.carpooling.network.model.BaseRequest;
import com.taurus.carpooling.network.model.placemarker.PlaceMarksWrapper;

import io.reactivex.Observable;

public interface CarPoolingApi {

    Observable<PlaceMarksWrapper> getCarFeeds(BaseRequest request);
}
