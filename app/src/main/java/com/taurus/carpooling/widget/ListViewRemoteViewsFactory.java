package com.taurus.carpooling.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.taurus.carpooling.R;
import com.taurus.carpooling.baseadapter.model.GenericItem;
import com.taurus.carpooling.network.CarPoolingApi;
import com.taurus.carpooling.network.model.BaseRequest;
import com.taurus.carpooling.network.retrofit.RetrofitCarPoolingApi;
import com.taurus.carpooling.placemarker.adapter.model.PlaceMarkerUIModel;
import com.taurus.carpooling.repository.PlaceMarker;
import com.taurus.carpooling.util.SharedPreferenceHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ListViewRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private List<GenericItem> listItemList;
    private Context context = null;
    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private CarPoolingApi api;
    private boolean isDataLoaded = false;
    private SharedPreferenceHelper sharedPreferenceHelper;

    public ListViewRemoteViewsFactory(Context context, Intent intent) {

        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

    }

    @Override
    public void onCreate() {

        listItemList = new ArrayList<>();

        sharedPreferenceHelper = new SharedPreferenceHelper(context);
        sharedPreferenceHelper.putBoolean(SharedPreferenceHelper.DATA_REFRESHED, true);

        api = new RetrofitCarPoolingApi();

    }

    @Override
    public void onDataSetChanged() {

        if (!isDataLoaded) {

            Boolean isRefreshClicked =  sharedPreferenceHelper.getBoolean(SharedPreferenceHelper.DATA_REFRESHED, false);

            if(isRefreshClicked) {

                final BaseRequest request = new BaseRequest();

                api.getCarFeeds(request)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(PlaceMarker::createList)
                        .subscribe(this::handleResponse, this::handleError);

            }

        }

    }

    private void handleResponse(List<PlaceMarker> data) {


        if (data != null && !data.isEmpty()) {

            sharedPreferenceHelper.putBoolean(SharedPreferenceHelper.DATA_REFRESHED, false);

            listItemList = new ArrayList<>(PlaceMarkerUIModel.createList(data));
            isDataLoaded = true;
            AppWidgetManager.getInstance(context).notifyAppWidgetViewDataChanged(appWidgetId, R.id.listViewWidget);

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.carpooling_widget_layout);
            setLastUpdateDate(remoteViews);
            setRefreshButton(remoteViews);

        }

    }


    private void handleError(Throwable throwable) {

    }

    private void setLastUpdateDate(RemoteViews remoteViews) {

        java.util.Calendar calendar = java.util.Calendar.getInstance();
        String time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.getTime());
        remoteViews.setTextViewText(R.id.widgetTextViewLastUpdate, time);

    }

    private void setRefreshButton(RemoteViews remoteViews) {

        remoteViews.setViewVisibility(R.id.widgetImageViewRefresh, View.VISIBLE);
        remoteViews.setViewVisibility(R.id.widgetProgressBar, View.INVISIBLE);
        ComponentName componentName = new ComponentName(context, CarpoolingWidgetProvider.class);
        AppWidgetManager.getInstance(context).updateAppWidget(componentName, remoteViews);

    }

    @Override
    public void onDestroy() {
        listItemList.clear();
    }

    @Override
    public int getCount() {
        return listItemList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {

        RemoteViews remoteView = null;

        if(listItemList != null && listItemList.size() > 0 && i < listItemList.size()) {

            isDataLoaded = false;
            GenericItem item = listItemList.get(i);

            if (item instanceof PlaceMarkerUIModel) {

                PlaceMarkerUIModel placeMarker = (PlaceMarkerUIModel) item;

                remoteView = new RemoteViews(context.getPackageName(), R.layout.widget_carpooling_item);

                remoteView.setTextViewText(R.id.markerTextViewName, placeMarker.getName());
                remoteView.setTextViewText(R.id.markerTextViewLongitude, String.valueOf(placeMarker.getLongitude()));
                remoteView.setTextViewText(R.id.markerTextViewLatitude, String.valueOf(placeMarker.getLatitude()));

            }

        }

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}
