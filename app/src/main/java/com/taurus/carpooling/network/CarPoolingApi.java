package com.taurus.carpooling.network;

import com.taurus.carpooling.network.model.BaseRequest;
import com.taurus.carpooling.network.model.placemarker.PlaceMarksWrapper;

import io.reactivex.Flowable;

public interface CarPoolingApi {

    Flowable<PlaceMarksWrapper> getCarFeeds(BaseRequest request);
}
