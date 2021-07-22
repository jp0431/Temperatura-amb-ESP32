package com.example.MqttApp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import  org.eclipse.paho.client.mqttv3.*;

import com.owl93.dpb.CircularProgressView;

public class DadesActivity extends AppCompatActivity {
   public static CircularProgressView cp;
    public static CircularProgressView cp2;

    private static final int id =2;
  public static   TextView h;
    public static   TextView h2;
    MqttAndroidClient client=MQTT.client;  //  clienteMQTT este dispositivo
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dades);



         cp=(CircularProgressView)findViewById(R.id.gauge);
        cp2=(CircularProgressView)findViewById(R.id.gauge2);
        cp2.setMaxValue(40);
        cp.setMaxValue(40);
         callBk();
         h=(TextView)findViewById(R.id.hic);
        h2=(TextView)findViewById(R.id.hic2);


    }

    public void callBk() {
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                Toast.makeText(getApplicationContext(), "Desconnexió ", Toast.LENGTH_SHORT).show();
                MQTT.conexionBroker("", "", "", DadesActivity.this);
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {

                String msg = message.toString();
                Intent intent = new Intent(getApplication(), Widget.class);
                AppWidgetManager mAppWidgetManager = getApplicationContext().getSystemService(AppWidgetManager.class);

                intent.setAction(mAppWidgetManager.ACTION_APPWIDGET_UPDATE);
                int[] ids = mAppWidgetManager.getAppWidgetIds(new ComponentName(getApplication(), Widget.class));
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
                sendBroadcast(intent);
                if(topic.equals("/JAPASE/TEMP_BDN")) {
                    if (Build.VERSION.SDK_INT < 26) {
                        return;
                    }
                    NotificationManager notificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    NotificationChannel channel = new NotificationChannel("default",
                            "Channel name",
                            NotificationManager.IMPORTANCE_DEFAULT);
                    channel.setDescription("Channel description");
                    notificationManager.createNotificationChannel(channel);
                    System.out.println("Message " + msg);
                    NotificationCompat.Builder not = new NotificationCompat.Builder(DadesActivity.this, "default");

                    not.setSmallIcon(R.mipmap.ic_launcher);
                    not.setTicker("Noves dades");
                    not.setWhen(System.currentTimeMillis());
                    not.setContentTitle("Noves dades Badalona");
                    not.setContentText("Temperatura: "+ msg+"ºC");
                    Intent intent2 = new Intent(DadesActivity.this, DadesActivity.class);
                    PendingIntent pi= PendingIntent.getActivity(DadesActivity.this,0,intent2,PendingIntent.FLAG_UPDATE_CURRENT);
                    not.setContentIntent(pi);
                    NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                    nm.notify(id, not.build());
                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(DadesActivity.this, "default");

                    float msgIn = Float.parseFloat(msg);
                    cp2.setText(msg + "ºC");
                    cp2.setProgress(msgIn);
                    if (msgIn <= 10) {
                        cp2.setStrokeColor(Color.RED);
                    } else if (msgIn > 10 && msgIn <= 15) {
                        cp2.setStrokeColor(Color.YELLOW);
                    } else if (msgIn > 15 && msgIn < 27) {
                        cp2.setStrokeColor(Color.GREEN);
                    } else if (msgIn > 27 && msgIn < 30) {
                        cp2.setStrokeColor(Color.YELLOW);
                    } else if (msgIn >= 30) {
                        cp2.setStrokeColor(Color.RED);

                    }
                }
                if(topic.equals("/JAPASE/HeatIndex_BDN")){
                    h2.setText(msg);


                }
                if(topic.equals("/JAPASE/TEMP")) {
                    if (Build.VERSION.SDK_INT < 26) {
                        return;
                    }
                    NotificationManager notificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    NotificationChannel channel = new NotificationChannel("default",
                            "Channel name",
                            NotificationManager.IMPORTANCE_DEFAULT);
                    channel.setDescription("Channel description");
                    notificationManager.createNotificationChannel(channel);
                    System.out.println("Message " + msg);
                    NotificationCompat.Builder not = new NotificationCompat.Builder(DadesActivity.this, "default");

                    not.setSmallIcon(R.mipmap.ic_launcher);
                    not.setTicker("Noves dades");
                    not.setWhen(System.currentTimeMillis());
                    not.setContentTitle("Noves dades L'Ametlla del Vallés");
                    not.setContentText("Temperatura: "+ msg+"ºC");
                    Intent intent2 = new Intent(DadesActivity.this, DadesActivity.class);
                    PendingIntent pi= PendingIntent.getActivity(DadesActivity.this,0,intent2,PendingIntent.FLAG_UPDATE_CURRENT);
                    not.setContentIntent(pi);
                    NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                    nm.notify(id, not.build());
                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(DadesActivity.this, "default");

                    float msgIn = Float.parseFloat(msg);
                    cp.setText(msg + "ºC");
                    cp.setProgress(msgIn);
                    if (msgIn <= 10) {
                        cp.setStrokeColor(Color.RED);
                    } else if (msgIn > 10 && msgIn <= 15) {
                        cp.setStrokeColor(Color.YELLOW);
                    } else if (msgIn > 15 && msgIn < 27) {
                        cp.setStrokeColor(Color.GREEN);
                    } else if (msgIn > 27 && msgIn < 30) {
                        cp.setStrokeColor(Color.YELLOW);
                    } else if (msgIn >= 30) {
                        cp.setStrokeColor(Color.RED);

                    }
                }
                if(topic.equals("/JAPASE/HeatIndex")){
                    h.setText(msg);


                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

    }
}