package com.taurus.carpooling.database.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.taurus.carpooling.database.Config;
import com.taurus.carpooling.repository.PlaceMarker;
import com.taurus.carpooling.util.ListConverter;

import java.util.List;

@Entity(tableName = Config.PLACE_MARKER_TABLE_NAME)
public class PlaceMarkerDatabaseModel {

    @PrimaryKey
    private String address;

    private String longitude;

    private String latitude;

    private String engineType;

    private String exterior;

    private int fuel;

    private String interior;

    private String name;

    private String vin;

    public PlaceMarkerDatabaseModel() {
    }

    public static List<PlaceMarker> createList(List<PlaceMarkerDatabaseModel> categoryList) {
        return ListConverter.convert(categoryList, item -> create(item));
    }

    private static PlaceMarker create(PlaceMarkerDatabaseModel item) {

        final PlaceMarker model = new PlaceMarker();

        model.setAddress(item.getAddress());
        model.setLatitude(item.getLatitude());
        model.setLongitude(item.getLongitude());
        model.setEngineType(item.getEngineType());
        model.setExterior(item.getExterior());
        model.setInterior(item.getInterior());
        model.setName(item.getName());
        model.setFuel(item.getFuel());
        model.setVin(item.getVin());

        return model;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getExterior() {
        return exterior;
    }

    public void setExterior(String exterior) {
        this.exterior = exterior;
    }

    public int getFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public String getInterior() {
        return interior;
    }

    public void setInterior(String interior) {
        this.interior = interior;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
}
