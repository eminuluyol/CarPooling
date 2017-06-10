package com.taurus.carpooling.network.retrofit;

import com.taurus.carpooling.network.model.placemarker.PlaceMarksWrapper;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface APIRestEndpoints {

    @GET()
    Observable<List<PlaceMarksWrapper>> getCarFeeds();
}
