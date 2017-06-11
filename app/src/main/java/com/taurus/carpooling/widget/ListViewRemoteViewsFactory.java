package com.taurus.carpooling.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.widget.RemoteViewsService;

import com.taurus.carpooling.baseadapter.model.GenericItem;
import com.taurus.carpooling.network.CarPoolingApi;

import java.util.List;

public class ListViewRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private List<GenericItem> listItemList;
    private Context context = null;
    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private CarPoolingApi api;
    private boolean isDataLoaded = false;
    private  SharedPreferenceHelper sharedPreferenceHelper;

}
