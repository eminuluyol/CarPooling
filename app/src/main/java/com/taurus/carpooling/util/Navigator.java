package com.taurus.carpooling.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

@ActivityScope
public class Navigator {

    private final Activity activity;

    public Navigator(Activity activity) {
        this.activity = activity;
    }

}
