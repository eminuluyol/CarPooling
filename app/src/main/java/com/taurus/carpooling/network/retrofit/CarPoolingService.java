package com.taurus.carpooling.network.retrofit;

import com.taurus.carpooling.network.model.placemarker.PlaceMarksWrapper;

import io.reactivex.Flowable;
import retrofit2.http.GET;

public interface CarPoolingService {

    @GET("/wunderbucket/locations.json")
    Flowable<PlaceMarksWrapper> getCarFeeds();
}
