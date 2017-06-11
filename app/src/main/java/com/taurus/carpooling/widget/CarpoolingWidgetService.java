package com.taurus.carpooling.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class CarpoolingWidgetService extends RemoteViewsService {

    /*
	 * So pretty simple just defining the Adapter of the ListViewRemoteViewsFactory
	 * here Adapter is ListProvider
	 * */

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new ListViewRemoteViewsFactory(this.getApplicationContext(), intent));
    }

}
