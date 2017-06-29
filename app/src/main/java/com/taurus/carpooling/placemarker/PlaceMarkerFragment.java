package com.taurus.carpooling.placemarker;

import android.Manifest;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
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
import com.taurus.carpooling.placemarker.adapter.delegate.PlaceMarkerAdapterDelegate;
import com.taurus.carpooling.placemarker.adapter.model.PlaceMarkerUIModel;
import com.taurus.carpooling.placemarker.listener.MyMarkerClickListener;
import com.taurus.carpooling.placemarker.listener.MyOnMapReadyCallback;
import com.taurus.carpooling.repository.PlaceMarker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;


@RuntimePermissions
public class PlaceMarkerFragment extends BaseFragment<PlaceMarkerView, PlaceMarkerPresenter>
        implements PlaceMarkerView, SlidingUpPanelLayout.PanelSlideListener,
        GoogleMap.OnInfoWindowCloseListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final String TAG = PlaceMarkerFragment.class.getSimpleName();
    private static final String EXTRA_PLACE_MARKER = "place_marker";

    // Location updates intervals in sec
    private static final int UPDATE_INTERVAL = 15000; // 10 sec
    private static final int FATEST_INTERVAL = 5000; // 5 sec
    private static final int DISPLACEMENT = 10; // 10 meters

    private List<PlaceMarker> placeMarkers;
    private List<PlaceMarkerUIModel> placeMarkerUIList;
    private ArrayList<Marker> markerList;
    private GoogleApiClient googleApiClient;
    private GoogleMap mGoogleMap;
    private Location lastLocation;
    // boolean flag to toggle periodic location updates
    private boolean requestingLocationUpdates = false;
    private LocationRequest locationRequest;

    @BindView(R.id.placeMarkerRecyclerView)
    RecyclerView placeMarkerRecyclerView;

    @BindView(R.id.emptyView)
    NestedScrollView emptyView;

    @BindView(R.id.placeMarkerButtonOpenMap)
    ImageButton buttonOpenMap;

    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout slidingLayout;

    public static PlaceMarkerFragment newInstance(List<PlaceMarker> placeMarkers) {

        Bundle args = new Bundle();
        args.putParcelableArrayList(PlaceMarkerFragment.EXTRA_PLACE_MARKER, (ArrayList<PlaceMarker>) placeMarkers);

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
            RecyclerAdapter placeMarkerAdapter = RecyclerAdapter.with(new PlaceMarkerAdapterDelegate());
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

        buildGoogleApiClient();
        createLocationRequest();

        PlaceMarkerFragmentPermissionsDispatcher.askPermissionsWithCheck(this);
        PlaceMarkerFragmentPermissionsDispatcher.askPermissionsForCurrentLocationWithCheck(this);

    }

    protected synchronized void buildGoogleApiClient() {

        googleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    protected void createLocationRequest() {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FATEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setSmallestDisplacement(DISPLACEMENT);

    }

    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void askPermissions() {
        startLocationUpdates();
    }

    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void askPermissionsForCurrentLocation() {
        displayCurrentLocation(mGoogleMap);
    }

    @OnPermissionDenied({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    public void showDeniedPermission() {
        showError(getContext().getResources().getString(R.string.permission_denied));
    }

    @OnNeverAskAgain({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    public void showNeverAskAgain() {
        Toast.makeText(getContext(), R.string.permission_never_task, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    private void startLocationUpdates() {

        if (!googleApiClient.isConnected())
            return;

        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, locationRequest, this);

    }

    @Override
    public void onResume() {
        super.onResume();

        if (googleApiClient.isConnected() && requestingLocationUpdates) {
            askPermissions();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    protected void stopLocationUpdates() {

        if (googleApiClient.isConnected()) {

            LocationServices.FusedLocationApi.removeLocationUpdates(
                    googleApiClient, this);

            googleApiClient.disconnect();
        }
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
        toggleMap();
    }

    private void toggleMap() {

        if (slidingLayout.getPanelState().equals(SlidingUpPanelLayout.PanelState.COLLAPSED)) {
            slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
        } else {
            slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        }
    }

    @Override
    public void onPanelSlide(View panel, float slideOffset) {
        //Nothing to do
    }

    @Override
    public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState,
                                    SlidingUpPanelLayout.PanelState newState) {

        if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
            buttonOpenMap.setImageResource(R.drawable.ic_map);
        }

        if (newState == SlidingUpPanelLayout.PanelState.ANCHORED) {
            buttonOpenMap.setImageResource(R.drawable.ic_arrow_down);
        }

        if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
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

        supportMapFragment.getMapAsync(new MyOnMapReadyCallback(placeMarkerUIList) {
            @Override
            public void onMapReadyMarkers(GoogleMap googleMap, List<PlaceMarkerUIModel> placeMarkerList) {

                mGoogleMap = googleMap;
                drawPlaceMarkersOnMap(googleMap, placeMarkerList);
            }
        });

    }

    private void drawPlaceMarkersOnMap(GoogleMap googleMap, List<PlaceMarkerUIModel> placeMarkerList) {

        markerList = new ArrayList<>();

        // Add a marker in Map
        for (PlaceMarkerUIModel marker : placeMarkerList) {

            LatLng place = new LatLng(marker.getLatitude(), marker.getLongitude());
            markerList.add(googleMap.addMarker(new MarkerOptions().position(place)
                    .title(marker.getName())
                    .snippet(marker.getAddress())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car_location))));

        }

        // move the camera
        if (!placeMarkerList.isEmpty()) {
            PlaceMarkerUIModel item = placeMarkerList.get(0);
            LatLng firstPlace = new LatLng(item.getLatitude(), item.getLongitude());
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstPlace, 11.1f));
        }

        setInfoWindowAdapter(googleMap);
        googleMap.setOnMarkerClickListener(new MyMarkerClickListener(markerList));
        googleMap.setOnInfoWindowCloseListener(this);

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
                int padding = (int) getContext().getResources().getDimension(R.dimen.space_small);
                snippet.setPadding(padding, 0, padding, 0);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });
    }

    @Override
    public void onInfoWindowClose(Marker marker) {
        for (Marker m : markerList) {
            m.setVisible(true);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: " + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
        if (mGoogleMap != null) {
            askPermissionsForCurrentLocation();
        }

        if (requestingLocationUpdates) {
            askPermissions();
        }
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        googleApiClient.connect();
    }

    private void displayCurrentLocation(GoogleMap googleMap) {

        if (!googleApiClient.isConnected())
            return;

        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if (lastLocation != null) {
            double latitude = lastLocation.getLatitude();
            double longitude = lastLocation.getLongitude();

            LatLng place = new LatLng(latitude, longitude);
            googleMap.addMarker(new MarkerOptions().position(place)
                    .title(getString(R.string.current_location))
                    .snippet(latitude + " " + longitude));

        } else {
            showError(getString(R.string.location_couldnt_find));
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        lastLocation = location;
        askPermissionsForCurrentLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        PlaceMarkerFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

}
