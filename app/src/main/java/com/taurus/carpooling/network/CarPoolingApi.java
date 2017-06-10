package com.taurus.carpooling.network;

import com.taurus.carpooling.network.model.BaseRequest;
import com.taurus.carpooling.network.model.placemarker.PlaceMarksWrapper;

import java.util.List;

import io.reactivex.Observable;

public interface CarPoolingApi {

    Observable<List<PlaceMarksWrapper>> getCarFeeds(BaseRequest request);
}
