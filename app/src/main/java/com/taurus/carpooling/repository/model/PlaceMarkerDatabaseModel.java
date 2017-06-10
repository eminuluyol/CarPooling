package com.taurus.carpooling.repository.model;

import com.taurus.carpooling.network.model.placemarker.PlaceMarksWrapper;
import com.taurus.carpooling.network.model.placemarker.Placemark;
import com.taurus.carpooling.util.ListConverter;

import java.util.List;

public class PlaceMarkerDatabaseModel {

    private String address;

    private String longitude;

    private String latitude;

    private String engineType;

    private String exterior;

    private int fuel;

    private String interior;

    private String name;

    private String vin;

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

    public static List<PlaceMarkerDatabaseModel> createList(PlaceMarksWrapper placeMarksWrappers) {
        return ListConverter.convert(placeMarksWrappers.getPlacemarks(), item -> create(item));
    }

    private static PlaceMarkerDatabaseModel create(Placemark item) {

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

        return model;
    }

}
