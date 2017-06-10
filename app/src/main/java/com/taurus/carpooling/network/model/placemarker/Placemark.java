
package com.taurus.carpooling.network.model.placemarker;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Placemark {

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("coordinates")
    @Expose
    private List<Double> coordinates = null;

    @SerializedName("engineType")
    @Expose
    private String engineType;

    @SerializedName("exterior")
    @Expose
    private String exterior;

    @SerializedName("fuel")
    @Expose
    private int fuel;

    @SerializedName("interior")
    @Expose
    private String interior;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("vin")
    @Expose
    private String vin;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
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
