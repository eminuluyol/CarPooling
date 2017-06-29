
package com.taurus.carpooling.network.model.placemarker;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.taurus.carpooling.database.model.PlaceMarkerDatabaseModel;

import java.util.ArrayList;
import java.util.List;

public class PlaceMarksWrapper {

    @SerializedName("placemarks")
    @Expose
    private List<Placemark> placemarks = null;

    public List<Placemark> getPlacemarks() {
        return placemarks;
    }

    public void setPlacemarks(List<Placemark> placemarks) {
        this.placemarks = placemarks;
    }

    public List<PlaceMarkerDatabaseModel> createList() {

        List<PlaceMarkerDatabaseModel> list = new ArrayList<>();

        for(Placemark item : getPlacemarks()) {

            final PlaceMarkerDatabaseModel model = new PlaceMarkerDatabaseModel();

            model.setAddress(item.getAddress());
            model.setLongitude(String.valueOf(item.getCoordinates().get(0)));
            model.setLatitude(String.valueOf(item.getCoordinates().get(1)));
            model.setEngineType(item.getEngineType());
            model.setExterior(item.getExterior());
            model.setFuel(item.getFuel());
            model.setInterior(item.getInterior());
            model.setName(item.getName());
            model.setVin(item.getVin());

            list.add(model);
        }

        return list;
    }

}
