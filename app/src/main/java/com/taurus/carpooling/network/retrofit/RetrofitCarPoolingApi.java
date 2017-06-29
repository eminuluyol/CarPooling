package com.taurus.carpooling.network.retrofit;

import com.taurus.carpooling.network.CarPoolingApi;
import com.taurus.carpooling.network.model.BaseRequest;
import com.taurus.carpooling.network.model.placemarker.PlaceMarksWrapper;

import io.reactivex.Flowable;

public class RetrofitCarPoolingApi implements CarPoolingApi {

    public RetrofitCarPoolingApi() {
    }

    @Override
    public Flowable<PlaceMarksWrapper> getCarFeeds(BaseRequest request) {

        CarPoolingService endpoints = APIRestClient.getInstanceRx().create(CarPoolingService.class);
        return endpoints.getCarFeeds();

    }
}
