package com.example.tema3ivascuvlad;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "testChannelId";
            String description = "testChannelIdDesc";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("testChannelId", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent newIntent = new Intent(context, MainActivity.class);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, newIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        String toDoTitle = intent.getStringExtra("toDoTitle");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "testChannelId")
                                                .setContentIntent(pendingIntent)
                                                .setSmallIcon(R.drawable.ic_launcher_background)
                                                .setContentTitle("TO DO REMINDER:")
                                                .setContentText(toDoTitle)
                                                .setAutoCancel(true);

        notificationManager.notify(100, builder.build());
    }
}
