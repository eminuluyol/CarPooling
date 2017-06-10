package com.taurus.carpooling.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.taurus.carpooling.repository.model.PlaceMarkerDatabaseModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

@Singleton
public class CarPoolingDatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "carPoolingManager";

    // Category table name
    private static final String TABLE_PLACE_MARKERS = "placeMarkers";

    // Category Table Columns names
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_ENGINE_TYPE = "engineType";
    private static final String KEY_EXTERIOR = "exterior";
    private static final String KEY_FUEL = "fuel";
    private static final String KEY_INTERIOR = "interior";
    private static final String KEY_NAME = "name";
    private static final String KEY_VIN = "vin";


    public CarPoolingDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + TABLE_PLACE_MARKERS + "("
                + KEY_ADDRESS + " TEXT PRIMARY KEY,"
                + KEY_LONGITUDE + " TEXT,"
                + KEY_LATITUDE + " TEXT,"
                + KEY_ENGINE_TYPE + " TEXT,"
                + KEY_EXTERIOR + " TEXT,"
                + KEY_FUEL + " INTEGER,"
                + KEY_INTERIOR + " TEXT,"
                + KEY_NAME + " TEXT,"
                + KEY_VIN + " TEXT" + ")";

        sqLiteDatabase.execSQL(CREATE_CATEGORIES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACE_MARKERS);

        // Create tables again
        onCreate(sqLiteDatabase);

    }


    public void addPlaceMarker(PlaceMarkerDatabaseModel placeMarker) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_ADDRESS, placeMarker.getAddress());
        values.put(KEY_LONGITUDE, placeMarker.getLongitude());
        values.put(KEY_LATITUDE, placeMarker.getLatitude());
        values.put(KEY_ENGINE_TYPE, placeMarker.getEngineType());
        values.put(KEY_EXTERIOR, placeMarker.getExterior());
        values.put(KEY_FUEL, placeMarker.getFuel());
        values.put(KEY_INTERIOR, placeMarker.getInterior());
        values.put(KEY_NAME, placeMarker.getName());
        values.put(KEY_VIN, placeMarker.getVin());

        db.insert(TABLE_PLACE_MARKERS, null, values);
        db.close();

    }

    public List<PlaceMarkerDatabaseModel> addAllPlaceMarkers(List<PlaceMarkerDatabaseModel> placeMarkers) {

        if (getPlaceMarkerCount() == 0) {

            addAllItems(placeMarkers);

        } else {

            removeAll();
            addAllItems(placeMarkers);

        }

        return getAllPlaceMarkers();

    }

    private void addAllItems(List<PlaceMarkerDatabaseModel> placeMarkers) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (int i = 0; i < placeMarkers.size(); i++) {

            ContentValues values = new ContentValues();

            values.put(KEY_ADDRESS, placeMarkers.get(i).getAddress());
            values.put(KEY_LONGITUDE, placeMarkers.get(i).getLongitude());
            values.put(KEY_LATITUDE, placeMarkers.get(i).getLatitude());
            values.put(KEY_ENGINE_TYPE, placeMarkers.get(i).getEngineType());
            values.put(KEY_EXTERIOR, placeMarkers.get(i).getExterior());
            values.put(KEY_FUEL, placeMarkers.get(i).getFuel());
            values.put(KEY_INTERIOR, placeMarkers.get(i).getInterior());
            values.put(KEY_NAME, placeMarkers.get(i).getName());
            values.put(KEY_VIN, placeMarkers.get(i).getVin());

            db.insert(TABLE_PLACE_MARKERS, null, values);

        }

        db.close();
    }

    public List<PlaceMarkerDatabaseModel> getAllPlaceMarkers() {

        List<PlaceMarkerDatabaseModel> placeMarkers = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_PLACE_MARKERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {

            do {

                PlaceMarkerDatabaseModel placeMarker = new PlaceMarkerDatabaseModel();

                placeMarker.setAddress(cursor.getString(0));
                placeMarker.setLongitude(cursor.getString(1));
                placeMarker.setLatitude(cursor.getString(2));
                placeMarker.setEngineType(cursor.getString(3));
                placeMarker.setExterior(cursor.getString(4));
                placeMarker.setFuel(Integer.parseInt(cursor.getString(5)));
                placeMarker.setInterior(cursor.getString(5));
                placeMarker.setName(cursor.getString(6));
                placeMarker.setVin(cursor.getString(7));

                placeMarkers.add(placeMarker);

            } while (cursor.moveToNext());

        }

        return placeMarkers;

    }

    public int getPlaceMarkerCount() {

        String countQuery = "SELECT  * FROM " + TABLE_PLACE_MARKERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor != null && cursor.moveToFirst()) {
            if (cursor.getInt(0) == 0) return 0;
        }

        cursor.close();

        return cursor.getCount();

    }

    public void removeAll () throws SQLException {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_PLACE_MARKERS, null, null);

        db.close ();

    }

}
