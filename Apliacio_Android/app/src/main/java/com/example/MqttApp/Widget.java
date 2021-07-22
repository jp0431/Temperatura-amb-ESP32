package com.example.MqttApp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import org.eclipse.paho.android.service.MqttAndroidClient;
import com.owl93.dpb.CircularProgressView;

/**
 * Implementation of App Widget functionality.
 */
public class Widget extends AppWidgetProvider {

     private  static  MqttAndroidClient client = MQTT.client;
    private static String msg;
CircularProgressView cp;
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {


        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
         RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);


            views.setTextViewText(R.id.temp_bdn, DadesActivity.cp2.getText());
            views.setTextViewText(R.id.appwidget_text3, DadesActivity.cp.getText());





            // CharSequence widgetText = context.getString(R.string.appwidget_text);
            // Construct the RemoteViews object

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created

    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


}