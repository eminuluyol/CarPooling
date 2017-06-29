package com.taurus.carpooling.repository.local;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.taurus.carpooling.database.CarpoolingDb;
import com.taurus.carpooling.database.Config;
import com.taurus.carpooling.database.PlaceMarkerDao;
import com.taurus.carpooling.database.model.PlaceMarkerDatabaseModel;
import com.taurus.carpooling.repository.PlaceMarkerDataSource;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Flowable;

@Singleton
public class PlaceMarkerLocalDataSource implements PlaceMarkerDataSource {

    private PlaceMarkerDao placeMarkerDao;

    public PlaceMarkerLocalDataSource(Context context) {

        CarpoolingDb carpoolingDb = Room.databaseBuilder(context, CarpoolingDb.class, Config.DATABASE_NAME).build();
        this.placeMarkerDao = carpoolingDb.placeMarkerDao();

    }

    @Override
    public Flowable<List<PlaceMarkerDatabaseModel>> loadPlaceMarker(boolean forceRemote) {
        return placeMarkerDao.getAllPlaceMarkers();
    }

    @Override
    public void addPlaceMarker(PlaceMarkerDatabaseModel placeMarkerDatabaseModel) {
        placeMarkerDao.insert(placeMarkerDatabaseModel);
    }

    @Override
    public void clearData() {
        placeMarkerDao.deleteAll();
    }
}
