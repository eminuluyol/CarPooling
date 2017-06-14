package com.taurus.carpooling.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.taurus.carpooling.database.model.PlaceMarkerDatabaseModel;

@Database(entities = PlaceMarkerDatabaseModel.class, version = 1)
public abstract class CarpoolingDb extends RoomDatabase {

    public abstract PlaceMarkerDao placeMarkerDao();
}
