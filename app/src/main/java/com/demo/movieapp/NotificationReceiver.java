package com.demo.movieapp;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;

import androidx.core.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        String cinema = intent.getStringExtra("cinema");
        // Build notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "123")
                .setContentTitle("Remember to go the cinema!")
                .setContentText("Your movie " + title + " will be started after 1 hour. Please go to the " + cinema + " to enjoy!! ^^")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        // Notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(123, builder.build());
    }
}
