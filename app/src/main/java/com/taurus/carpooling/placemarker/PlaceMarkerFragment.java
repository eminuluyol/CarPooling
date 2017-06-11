package com.taurus.carpooling.placemarker;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.taurus.carpooling.R;
import com.taurus.carpooling.baseadapter.RecyclerAdapter;
import com.taurus.carpooling.baseadapter.model.GenericItem;
import com.taurus.carpooling.core.BaseFragment;
import com.taurus.carpooling.placemarker.adapter.PlaceMarkerAdapterDelegate;
import com.taurus.carpooling.placemarker.adapter.PlaceMarkerUIModel;
import com.taurus.carpooling.placemarker.listener.MyMarkerClickListener;
import com.taurus.carpooling.placemarker.listener.MyOnMapReadyCallback;
import com.taurus.carpooling.repository.model.PlaceMarkerDatabaseModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.taurus.carpooling.R.id.map;

public class PlaceMarkerFragment extends BaseFragment<PlaceMarkerView, PlaceMarkerPresenter>
        implements PlaceMarkerView, SlidingUpPanelLayout.PanelSlideListener {

    private static final String EXTRA_PLACE_MARKER = "place_marker";

    private List<PlaceMarkerDatabaseModel> placeMarkers;
    private List<PlaceMarkerUIModel> placeMarkerUIList;
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

            placeMarkerUIList = new ArrayList<>(PlaceMarkerUIModel.createList(placeMarkers));
            List<GenericItem> placeMarkerList = new ArrayList<>(PlaceMarkerUIModel.createList(placeMarkers));

            placeMarkerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            placeMarkerAdapter = RecyclerAdapter.with(new PlaceMarkerAdapterDelegate());
            placeMarkerRecyclerView.setAdapter(placeMarkerAdapter);
            placeMarkerAdapter.swapItems(placeMarkerList);

        } else {
            getPresenter().showEmptyView();
        }

        getPresenter().checkGoogleServiceAvailability();

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
    public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState,
                                    SlidingUpPanelLayout.PanelState newState) {

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
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(map);

        if (supportMapFragment == null) {
            supportMapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(map, supportMapFragment).commit();
        }

        supportMapFragment.getMapAsync(new MyOnMapReadyCallback(placeMarkerUIList) {
            @Override
            public void onMapReadyMarkers(GoogleMap googleMap, List<PlaceMarkerUIModel> placeMarkerList) {
                drawPlaceMarkersOnMap(googleMap, placeMarkerList);
            }
        });

    }

    private void drawPlaceMarkersOnMap(GoogleMap googleMap, List<PlaceMarkerUIModel> placeMarkerList) {

        // Add a marker in Map
        for (PlaceMarkerUIModel marker : placeMarkerList) {

            LatLng place = new LatLng(marker.getLatitude(), marker.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(place)
                    .title(marker.getName())
                    .snippet(marker.getAddress())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car_location)));
        }

        // move the camera
        if (!placeMarkerList.isEmpty()) {
            PlaceMarkerUIModel item = placeMarkerList.get(0);
            LatLng firstPlace = new LatLng(item.getLatitude(), item.getLongitude());
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstPlace, 11.1f));
        }

        setInfoWindowAdapter(googleMap);
        googleMap.setOnMarkerClickListener(new MyMarkerClickListener());

    }

    private void setInfoWindowAdapter(GoogleMap googleMap) {

        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                LinearLayout info = new LinearLayout(getContext());
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(getContext());
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(getContext());
                int padding = (int)getContext().getResources().getDimension(R.dimen.space_small);
                snippet.setPadding(padding, 0, padding, 0);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });
    }

}
