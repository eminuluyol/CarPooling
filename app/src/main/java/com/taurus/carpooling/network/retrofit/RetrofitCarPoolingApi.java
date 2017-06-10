package com.taurus.carpooling.network.retrofit;

import com.taurus.carpooling.network.CarPoolingApi;
import com.taurus.carpooling.network.model.BaseRequest;
import com.taurus.carpooling.network.model.placemarker.PlaceMarksWrapper;

import java.util.List;

import io.reactivex.Observable;

public class RetrofitCarPoolingApi implements CarPoolingApi {

    @Override
    public Observable<List<PlaceMarksWrapper>> getCarFeeds(BaseRequest request) {

        APIRestEndpoints endpoints = APIRestClient.getInstanceRx().create(APIRestEndpoints.class);
        return endpoints.getCarFeeds();

    }

}
