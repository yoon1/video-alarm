package com.example.videoalarm.utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.example.videoalarm.activity.YoutubeActivity;

import androidx.annotation.Nullable;

public class VideoPlayingService extends Service {


    public VideoPlayingService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Intent intent = new Intent(this, YoutubeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // startActivity를 쓰지말라는데.. 일단 놔둬보자.
        String id = intent.getStringExtra("id");
        intent.putExtra("id", id);
        SqlLiteUtil.getInstance().selectAlarm(Integer.parseInt(id));
        startActivity(intent);
    }
/*
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

    }
 */



}
