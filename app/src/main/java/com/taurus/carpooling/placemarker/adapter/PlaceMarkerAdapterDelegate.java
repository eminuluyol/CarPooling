package com.taurus.carpooling.placemarker.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.taurus.carpooling.R;
import com.taurus.carpooling.baseadapter.BaseAdapterDelegate;
import com.taurus.carpooling.baseadapter.model.GenericItem;

import java.util.List;

public class PlaceMarkerAdapterDelegate extends BaseAdapterDelegate<PlaceMarkerUIModel, GenericItem,
        PlaceMarkerViewHolder> {

    @Override
    protected boolean isForViewType(@NonNull GenericItem item, @NonNull List<GenericItem> items, int position) {
        return item instanceof PlaceMarkerUIModel;
    }

    @NonNull
    @Override
    protected PlaceMarkerViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new PlaceMarkerViewHolder(parent, R.layout.list_item_place_marker);
    }

    @Override
    protected void onBindViewHolder(@NonNull PlaceMarkerUIModel item, @NonNull PlaceMarkerViewHolder viewHolder, @NonNull List<Object> payloads) {
        viewHolder.bind(item);
    }
}
