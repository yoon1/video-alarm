package com.example.videoalarm.utils;

import android.app.ActivityManager;
import android.app.Service;
import android.bluetooth.BluetoothManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.preference.PreferenceManager;

import com.example.videoalarm.activity.MainActivity;
import com.example.videoalarm.activity.YoutubeActivity;

import java.util.List;

import androidx.annotation.Nullable;

public class VideoPlayingService extends Service {
    int startId;
    int id;
    String videoId = "";

    boolean isRunning;
    public VideoPlayingService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if ( intent.getStringExtra("videoId") != null){
            videoId = intent.getStringExtra("videoId");
        }
        if ( intent.getStringExtra("id") != null){
            id = Integer.parseInt(intent.getStringExtra("id"));
        }
        try {
            Thread.sleep(1000);
        } catch (Exception e) {}
        String getState = intent.getExtras().getString("state");
        assert getState != null;
        switch( getState) {
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                break;
            default :
                startId = 0;
                break;
        }

        // 알람 재생 X & 알람음 등록!
        if ( !this.isRunning && startId == 1) {
            MyDebug.log("ON SERVICE ON START COMMAND 1 실행 : + " + videoId);
            MyDebug.log("ON SERVICE ON START COMMAND 1 실행 : + " + id);
            startVideo(id, videoId);
            this.isRunning = true;
            this.startId= 0;
            MyDebug.log("ON SERVICE ON START COMMAND 1 실행 완료 : + " + videoId);
        }
        else if (this.isRunning && startId == 0) {
            MyDebug.log("ON SERVICE ON START COMMAND 2 실행 : + " + videoId);
            this.isRunning = false;
            this.startId = 0;
            MyDebug.log("ON SERVICE ON START COMMAND 2 실행 완료: + " + videoId);

        }
        else if (!this.isRunning && startId == 0) {
            MyDebug.log("ON SERVICE ON START COMMAND 3 실행 완료: + " + videoId);
            this.isRunning = false;
            this.startId = 0;
            MyDebug.log("ON SERVICE ON START COMMAND 3 실행 완료: + " + videoId);
        }
        else if (this.isRunning && startId == 1) {
            MyDebug.log("ON SERVICE ON START COMMAND 4 실행 완료: + " + videoId);
            startVideo(id, videoId);
            this.isRunning = true;
            this.startId = 1;
            MyDebug.log("ON SERVICE ON START COMMAND 4 실행 완료: + " + videoId);
        }
        else {
            MyDebug.log("ON SERVICE ON START COMMAND 5 실행 완료: + " + videoId);
        }
        return START_NOT_STICKY;
    }
    public void startVideo (int alarmId, String videoId) {
        Intent showIntent = new Intent(getApplicationContext(), YoutubeActivity.class);
        SqlLiteUtil.getInstance().updateEnable(alarmId); // need update function
        showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        showIntent.putExtra("videoId", videoId);
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("isActive", false)) {
            MyDebug.log(" PREFERENCE MANAGER : TRUE ");
        }
        else {
            MyDebug.log(" PREFERENCE MANAGER : FALSE ");
        }
        startActivity(showIntent);
    }
}
