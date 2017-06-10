package com.taurus.carpooling.placemarker;

import com.taurus.carpooling.core.BasePresenter;
import com.taurus.carpooling.core.injection.Injector;

public class PlaceMarkerPresenter extends BasePresenter<PlaceMarkerView> {

    PlaceMarkerPresenter() {
        Injector.getInstance().getActivityComponent().inject(this);
    }
}
