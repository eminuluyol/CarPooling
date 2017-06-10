package com.taurus.carpooling.placemarker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.taurus.carpooling.R;
import com.taurus.carpooling.core.BaseFragment;
import com.taurus.carpooling.repository.model.PlaceMarkerDatabaseModel;

import java.util.ArrayList;
import java.util.List;

public class PlaceMarkerFragment extends BaseFragment<PlaceMarkerView, PlaceMarkerPresenter>
        implements PlaceMarkerView {

    private static final String EXTRA_PLACE_MARKER = "place_marker";
    private List<PlaceMarkerDatabaseModel> placeMarkers;

    public static PlaceMarkerFragment newInstance(List<PlaceMarkerDatabaseModel> placeMarkers) {

        Bundle args = new Bundle();
        args.putParcelableArrayList(PlaceMarkerFragment.EXTRA_PLACE_MARKER, (ArrayList<PlaceMarkerDatabaseModel>) placeMarkers);

        PlaceMarkerFragment fragment = new PlaceMarkerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_place_marker;
    }

    @Override
    public PlaceMarkerPresenter createPresenter() {
        return new PlaceMarkerPresenter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getBundleFromArgs();

        if (placeMarkers != null && !placeMarkers.isEmpty()) {

            Log.i("Size", "" + placeMarkers.size());

        }
    }

    private void getBundleFromArgs() {

        Bundle args = getArguments();
        if (args == null) return;
        placeMarkers = args.getParcelableArrayList(PlaceMarkerFragment.EXTRA_PLACE_MARKER);

    }


}
