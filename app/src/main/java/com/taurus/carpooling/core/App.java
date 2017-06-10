package com.taurus.carpooling.core;

import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Injector.getInstance().createApplicationScope(this);

    }
}