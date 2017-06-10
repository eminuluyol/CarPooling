package com.taurus.carpooling.placemarker.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taurus.carpooling.R;
import com.taurus.carpooling.baseadapter.viewholder.BaseViewHolder;

import butterknife.BindView;

public class PlaceMarkerViewHolder extends BaseViewHolder {

    @BindView(R.id.markerTextViewName)
    TextView textViewName;

    @BindView(R.id.markerTextViewLongitude)
    TextView textViewLongitude;

    @BindView(R.id.markerTextViewLatitude)
    TextView textViewLatitude;

    @BindView(R.id.markerTextViewEngineType)
    TextView textViewEngineType;

    @BindView(R.id.markerTextViewFuel)
    TextView textViewFuel;

    @BindView(R.id.markerTextViewVin)
    TextView textViewVin;

    @BindView(R.id.markerTextViewExterior)
    TextView textViewExterior;

    @BindView(R.id.markerTextViewinterior)
    TextView textViewInterior;

    @BindView(R.id.markerTextViewAddress)
    TextView textViewAddress;

    public PlaceMarkerViewHolder(@NonNull ViewGroup parentView, int layoutId) {
        super(parentView, layoutId);
    }


    public void bind(PlaceMarkerUIModel item) {

         textViewName.setText(item.getName());
         textViewLongitude.setText(String.valueOf(item.getLongitude()));
         textViewLatitude.setText(String.valueOf(item.getLatitude()));
         textViewEngineType.setText(item.getEngineType());
         textViewFuel.setText(String.valueOf(item.getFuel()));
         textViewVin.setText(item.getVin());
         textViewExterior.setText(item.getExterior());
         textViewInterior.setText(item.getInterior());
         textViewAddress.setText(item.getAddress());

    }
}
