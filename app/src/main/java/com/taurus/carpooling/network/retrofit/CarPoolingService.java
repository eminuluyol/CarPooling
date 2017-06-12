package com.taurus.carpooling.network.retrofit;

import com.taurus.carpooling.network.model.BaseRequest;
import com.taurus.carpooling.network.model.placemarker.PlaceMarksWrapper;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface CarPoolingService {

    @GET("/wunderbucket/locations.json")
    Observable<PlaceMarksWrapper> getCarFeeds();
}
