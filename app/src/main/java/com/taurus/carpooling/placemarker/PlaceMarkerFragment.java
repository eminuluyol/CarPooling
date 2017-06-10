package com.taurus.carpooling.placemarker;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.SupportMapFragment;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.taurus.carpooling.R;
import com.taurus.carpooling.baseadapter.RecyclerAdapter;
import com.taurus.carpooling.baseadapter.model.GenericItem;
import com.taurus.carpooling.core.BaseFragment;
import com.taurus.carpooling.placemarker.adapter.PlaceMarkerAdapterDelegate;
import com.taurus.carpooling.placemarker.adapter.PlaceMarkerUIModel;
import com.taurus.carpooling.repository.model.PlaceMarkerDatabaseModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PlaceMarkerFragment extends BaseFragment<PlaceMarkerView, PlaceMarkerPresenter>
        implements PlaceMarkerView, SlidingUpPanelLayout.PanelSlideListener {

    private static final String EXTRA_PLACE_MARKER = "place_marker";

    private List<PlaceMarkerDatabaseModel> placeMarkers;
    private RecyclerAdapter placeMarkerAdapter;

    @BindView(R.id.placeMarkerRecyclerView)
    RecyclerView placeMarkerRecyclerView;

    @BindView(R.id.emptyView)
    NestedScrollView emptyView;

    @BindView(R.id.placeMarkerButtonOpenMap)
    ImageButton buttonOpenMap;

    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout slidingLayout;

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

    @NonNull
    @Override
    public PlaceMarkerPresenter createPresenter() {
        return new PlaceMarkerPresenter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getBundleFromArgs();

        slidingLayout.addPanelSlideListener(this);

        if (placeMarkers != null && !placeMarkers.isEmpty()) {

            List<GenericItem> placeMarkerList = new ArrayList<>(PlaceMarkerUIModel.createList(placeMarkers));

            placeMarkerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            placeMarkerAdapter = RecyclerAdapter.with(new PlaceMarkerAdapterDelegate());
            placeMarkerRecyclerView.setAdapter(placeMarkerAdapter);
            placeMarkerAdapter.swapItems(placeMarkerList);

        } else {
            getPresenter().showEmptyView();
        }

        getPresenter().onGoogleServiceAvailability(getContext());

    }

    private void getBundleFromArgs() {

        Bundle args = getArguments();
        if (args == null) return;
        placeMarkers = args.getParcelableArrayList(PlaceMarkerFragment.EXTRA_PLACE_MARKER);

    }

    @Override
    public void showEmptyView() {
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyView() {
        emptyView.setVisibility(View.GONE);
    }

    @OnClick(R.id.placeMarkerButtonOpenMap)
    public void onViewClicked(View view) {
        YoYo.with(Techniques.BounceInUp)
                .duration(850)
                .playOn(view);
        toogleMap();
    }

    private void toogleMap() {

        if (slidingLayout.getPanelState().equals(SlidingUpPanelLayout.PanelState.COLLAPSED)) {
            slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
        } else {
            slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        }
    }

    @Override
    public void onPanelSlide(View panel, float slideOffset) {

    }

    @Override
    public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

        if(newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
            buttonOpenMap.setImageResource(R.drawable.ic_map);
        }

        if(newState == SlidingUpPanelLayout.PanelState.ANCHORED) {
            buttonOpenMap.setImageResource(R.drawable.ic_arrow_down);
        }

        if(newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
            buttonOpenMap.setImageResource(R.drawable.ic_arrow_down);
        }

    }

    @Override
    public void showInformationDialog(GoogleApiAvailability apiAvailability, int isAvailable) {

        Dialog dialog = apiAvailability.getErrorDialog(getActivity(), isAvailable, 0);
        dialog.show();

    }
    @Override
    public void showGoogleServiceError() {
        Toast.makeText(getContext(), getResources().getString(R.string.google_maps_error), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMap() {

        FragmentManager fm = getActivity().getSupportFragmentManager();
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (supportMapFragment == null) {
            supportMapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, supportMapFragment).commit();
        }

    }
}
