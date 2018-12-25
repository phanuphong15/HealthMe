package com.example.mulletz.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;

    }
    @Override
    public int onStartCommand(Intent intent,int flags ,int startID){
        if(true){
            Notification notif = new Notification.Builder(getBaseContext())
                    .setSmallIcon(R.drawable.app_login)
                    .setContentTitle("Hello")
                    .setContentText("awdawdawdawdawd")
                    .build();
            NotificationManager notifMan = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            notifMan.notify(12345,notif);
        }
        return Service.START_NOT_STICKY;
    }
}
