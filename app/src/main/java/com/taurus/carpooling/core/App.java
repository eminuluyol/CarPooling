package com.taurus.carpooling.core;

import android.app.Application;

import com.taurus.carpooling.core.injection.Injector;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Injector.getInstance().createApplicationScope(this);

    }
}