package com.taurus.carpooling.placemarker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.taurus.carpooling.R;
import com.taurus.carpooling.core.BaseFragment;
import com.taurus.carpooling.core.BaseSimpleActivity;
import com.taurus.carpooling.repository.model.PlaceMarkerDatabaseModel;

import java.util.ArrayList;
import java.util.List;

public class PlaceMarkerActivity extends BaseSimpleActivity {

    private static final String EXTRA_PLACE_MARKER = "place_marker";

    private  List<PlaceMarkerDatabaseModel> placeMarkers;

    public static Intent newIntent(Context context, List<PlaceMarkerDatabaseModel> placeMarkers) {

        Intent intent = new Intent(context, PlaceMarkerActivity.class);
        intent.putParcelableArrayListExtra(PlaceMarkerActivity.EXTRA_PLACE_MARKER, new ArrayList<>(placeMarkers));

        return intent;

    }

    @Nullable
    @Override
    protected BaseFragment getContainedFragment() {
        return PlaceMarkerFragment.newInstance(placeMarkers);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getBundleArguments();
        super.onCreate(savedInstanceState);

        setTitle(R.string.place_marker_title);

    }

    private void getBundleArguments() {

        Intent extras = getIntent();

        if (extras != null) {
            placeMarkers = extras.getParcelableArrayListExtra(PlaceMarkerActivity.EXTRA_PLACE_MARKER);
        }

    }
}
