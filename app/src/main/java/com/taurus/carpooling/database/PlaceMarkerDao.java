package com.taurus.carpooling.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.taurus.carpooling.database.model.PlaceMarkerDatabaseModel;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface PlaceMarkerDao {

    @Query("SELECT * FROM " + Config.PLACE_MARKER_TABLE_NAME)
    Flowable<List<PlaceMarkerDatabaseModel>> getAllPlaceMarkers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PlaceMarkerDatabaseModel placeMarkerDatabaseModel);

    @Query("DELETE FROM " + Config.PLACE_MARKER_TABLE_NAME)
    void deleteAll();

}
