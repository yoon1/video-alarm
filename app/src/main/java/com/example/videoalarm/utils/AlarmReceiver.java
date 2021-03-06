package com.example.videoalarm.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.videoalarm.R;
import com.example.videoalarm.activity.YoutubeActivity;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;

public class AlarmReceiver extends BroadcastReceiver {

    Context context;

    private static final String CHANNEL_ID = "com.example.videoalarm.view.channelId";

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        MyDebug.log("IM ALARM RECEIVER");
        Intent notificationIntent = new Intent(context, VideoPlayingService.class);
        String id = intent.getStringExtra("id");
        String videoId= intent.getStringExtra("videoId");
        String alarmNote= intent.getStringExtra("alarmNote");
        String state = intent.getExtras().getString("state");

        MyDebug.log("IM ALARM RECEIVER ID : " + id);
        MyDebug.log("IM ALARM RECEIVER VIDEO ID : " + videoId);
        MyDebug.log("IM ALARM RECEIVER ALARM NOTE : " + alarmNote);
        MyDebug.log("IM ALARM RECEIVER STATE : " + state );

        notificationIntent.putExtra("id", id );
        notificationIntent.putExtra("alarmNote", alarmNote);
        notificationIntent.putExtra("videoId", videoId);
        notificationIntent.putExtra("state", state);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(YoutubeActivity.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(context);
        Notification notification = builder.setContentTitle("예약한 비디오가 도착했어요.")
                .setContentText("NOTE : " + alarmNote)
                .setTicker("그떄보자 : 예약한 비디오가 도착했어요!!")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentIntent(pendingIntent).build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID);
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "NotificationDemo",
                    IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
            this.context.startForegroundService(notificationIntent);
        }
        notificationManager.notify(0, notification);
        this.context.startService(notificationIntent);

    }
}