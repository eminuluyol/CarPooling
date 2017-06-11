package com.taurus.carpooling.placemarker.adapter.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.taurus.carpooling.baseadapter.model.GenericItem;
import com.taurus.carpooling.repository.model.PlaceMarkerDatabaseModel;
import com.taurus.carpooling.util.ListConverter;

import java.util.List;

public class PlaceMarkerUIModel extends GenericItem implements Parcelable{

    private String address;

    private Double longitude;

    private Double latitude;

    private String engineType;

    private String exterior;

    private int fuel;

    private String interior;

    private String name;

    private String vin;

    public PlaceMarkerUIModel() {
    }

    protected PlaceMarkerUIModel(Parcel in) {
        super(in);
        address = in.readString();
        engineType = in.readString();
        exterior = in.readString();
        fuel = in.readInt();
        interior = in.readString();
        name = in.readString();
        vin = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(address);
        dest.writeString(engineType);
        dest.writeString(exterior);
        dest.writeInt(fuel);
        dest.writeString(interior);
        dest.writeString(name);
        dest.writeString(vin);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PlaceMarkerUIModel> CREATOR = new Creator<PlaceMarkerUIModel>() {
        @Override
        public PlaceMarkerUIModel createFromParcel(Parcel in) {
            return new PlaceMarkerUIModel(in);
        }

        @Override
        public PlaceMarkerUIModel[] newArray(int size) {
            return new PlaceMarkerUIModel[size];
        }
    };

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
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


    public static List<PlaceMarkerUIModel> createList(List<PlaceMarkerDatabaseModel> placeMarkers) {
        return ListConverter.convert(placeMarkers, item -> create(item));
    }

    private static PlaceMarkerUIModel create(PlaceMarkerDatabaseModel item) {

        final PlaceMarkerUIModel model = new PlaceMarkerUIModel();

        model.setAddress(item.getAddress());
        model.setLongitude(Double.parseDouble(item.getLongitude()));
        model.setLatitude(Double.parseDouble(item.getLatitude()));
        model.setEngineType(item.getEngineType());
        model.setExterior(item.getExterior());
        model.setFuel(item.getFuel());
        model.setInterior(item.getInterior());
        model.setName(item.getName());
        model.setVin(item.getVin());

        return model;
    }

}
