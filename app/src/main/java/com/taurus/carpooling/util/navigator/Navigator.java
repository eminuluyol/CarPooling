package com.taurus.carpooling.util.navigator;

import android.app.Activity;

import com.taurus.carpooling.core.injection.ActivityScope;

@ActivityScope
public class Navigator {

    private final Activity activity;

    public Navigator(Activity activity) {
        this.activity = activity;
    }

}
