package com.taurus.carpooling.placemarker;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.taurus.carpooling.core.BaseFragment;
import com.taurus.carpooling.core.BaseSimpleActivity;
import com.taurus.carpooling.repository.model.PlaceMarkerDatabaseModel;

import java.util.List;

public class PlaceMarkerActivity extends BaseSimpleActivity {

    private static final String EXTRA_CATEGORY_DETAIL = "category_detail";

    @Nullable
    @Override
    protected BaseFragment getContainedFragment() {
        return null;
    }

    public static Intent newIntent(Activity activity, List<PlaceMarkerDatabaseModel> placeMarkers) {
        return null;
    }
}
