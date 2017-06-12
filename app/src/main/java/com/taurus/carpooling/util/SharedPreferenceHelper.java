package com.taurus.carpooling.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

@Singleton
public class SharedPreferenceHelper {

    public static final String DATA_REFRESHED = "data_refreshed";

    private final Context context;

    public SharedPreferenceHelper(@NonNull Context context) {
        this.context = context;
    }

    private SharedPreferences getSharedPreference() {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public SharedPreferences getSharedPreferences(String name, int mode) {
        return context.getSharedPreferences(name, mode);
    }

    private SharedPreferences.Editor getSharedPreferenceEditor() {
        return getSharedPreference().edit();
    }

    /**
     * Set a int shared preference
     * @param key - Key to set shared preference
     * @param value - Value for the key
     */
    public void putInt(String key, int value) {

        SharedPreferences.Editor editor = getSharedPreferenceEditor();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * Get a int shared preference
     * @param defValue - Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    public int getInt(String key, int defValue) {

        SharedPreferences preferences = getSharedPreference();

        return preferences.getInt(key, defValue);

    }

    /**
     * Set a String shared preference
     * @param key - Key to set shared preference
     * @param value - Value for the key
     */
    public void putString(String key, String value) {

        SharedPreferences.Editor editor = getSharedPreferenceEditor();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Get a string shared preference
     * @param defValue - Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    public String getString(String key, String defValue) {

        SharedPreferences preferences = getSharedPreference();

        return preferences.getString(key, defValue);

    }

    /**
     * Set a float shared preference
     * @param key - Key to set shared preference
     * @param value - Value for the key
     */
    public void putFloat(String key, float value) {

        SharedPreferences.Editor editor = getSharedPreferenceEditor();
        editor.putFloat(key, value);
        editor.apply();
    }

    /**
     * Get a float shared preference
     * @param defValue - Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    public float getFloat(String key, float defValue) {

        SharedPreferences preferences = getSharedPreference();

        return preferences.getFloat(key, defValue);

    }

    /**
     * Set a long shared preference
     * @param key - Key to set shared preference
     * @param value - Value for the key
     */
    public void putLong(String key, long value) {

        SharedPreferences.Editor editor = getSharedPreferenceEditor();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * Get a long shared preference
     * @param defValue - Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    public float getLong(String key, long defValue) {

        SharedPreferences preferences = getSharedPreference();

        return preferences.getLong(key, defValue);

    }

    /**
     * Set a boolean shared preference
     * @param key - Key to set shared preference
     * @param value - Value for the key
     */
    public void putBoolean(String key, boolean value) {

        SharedPreferences.Editor editor = getSharedPreferenceEditor();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * Get a boolean shared preference
     * @param defValue - Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    public boolean getBoolean(String key, boolean defValue) {

        SharedPreferences preferences = getSharedPreference();

        return preferences.getBoolean(key, defValue);

    }

    /**
     * Remove key-value from shared preference
     * @param key - Key to delete from shared preference
     */
    public void remove(String key) {

        SharedPreferences.Editor editor = getSharedPreferenceEditor();
        editor.remove(key);
        editor.apply();
    }

    /**
     * Clear all the data from shared preferences
     */
    public void clearAllDataFromSharedPreference() {

        SharedPreferences.Editor editor = getSharedPreferenceEditor();
        editor.clear();
        editor.apply();
    }

}
