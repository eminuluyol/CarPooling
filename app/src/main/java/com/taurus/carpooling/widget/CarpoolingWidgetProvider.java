package com.taurus.carpooling.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.taurus.carpooling.R;

public class CarpoolingWidgetProvider extends AppWidgetProvider {

    public static final String DATA_FETCHED = "com.taurus.carpooling.widget.DATA_FETCHED";
    public static final String DATA_REFRESHED = "com.taurus.carpooling.widget.DATA_REFRESHED";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

		/*int[] appWidgetIds holds ids of multiple instance of your widget
         * meaning you are placing more than one widgets on your homescreen*/
        for (int appWidgetId : appWidgetIds) {

            RemoteViews remoteViews = updateWidgetListView(context, appWidgetId);
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.listViewWidget);

        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private RemoteViews updateWidgetListView(Context context, int appWidgetId) {

        //which layout to show on widget
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.carpooling_widget_layout);

        //RemoteViews Service needed to provide adapter for ListView
        Intent intent = new Intent(context, CarpoolingWidgetService.class);
        //passing app widget id to that RemoteViews Service
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        //setting a unique Uri to the intent
        //don't know its purpose to me right now
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        //setting adapter to listview of the widget
        remoteViews.setRemoteAdapter(R.id.listViewWidget, intent);
        //setting an empty view in case of no data
        remoteViews.setEmptyView(R.id.listViewWidget, R.id.empty_view);

        //Refresh Intent
        // Bind the click intent for the refresh button on the widget
        final Intent refreshIntent = new Intent(context, CarpoolingWidgetProvider.class);
        refreshIntent.setAction(CarpoolingWidgetProvider.DATA_REFRESHED);
        refreshIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        final PendingIntent refreshPendingIntent = PendingIntent.getBroadcast(context, appWidgetId,
                refreshIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widgetImageViewRefresh, refreshPendingIntent);

        return remoteViews;

    }

}
