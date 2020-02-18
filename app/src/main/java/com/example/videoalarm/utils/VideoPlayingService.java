package com.example.videoalarm.utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.example.videoalarm.activity.YoutubeActivity;

import androidx.annotation.Nullable;

public class VideoPlayingService extends Service {

    int startId;
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
        //Intent intent = new Intent(this, YoutubeActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // startActivity를 쓰지말라는데.. 일단 놔둬보자.
        /*String id = intent.getStringExtra("id");
        String videoId = intent.getStringExtra("videoId");
        intent.putExtra("id", id );
        intent.putExtra("videoId", videoId);*/
        //MyDebug.log("VIdeoPlaying SERVICE ID : + " + id );
        //MyDebug.log("VIdeoPlaying SERVICE videoId : + " + videoId);
        //SqlLiteUtil.getInstance().selectAlarm(Integer.parseInt(id));
        //startActivity(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if ( intent.getStringExtra("videoId") != null){
            videoId = intent.getStringExtra("videoId");
            MyDebug.log("IM VideoPlayingService videoId : " + videoId );
        }
        try {
            Thread.sleep(1000);
        } catch (Exception e) {}
        String getState = intent.getExtras().getString("state");
        MyDebug.log("onService - SERVICE START COMMAND" + getState);
        assert getState != null;
        switch( getState) {
            case "alarm on":
                MyDebug.log("ON SERVICE ON START SWITCH 1");
                startId = 1;
                break;
            case "alarm off":
                MyDebug.log("ON SERVICE ON START SWITCH 2");
                startId = 0;
                break;
            default :
                MyDebug.log("ON SERVICE ON START SWITCH 3");
                startId = 0;
                break;
        }

        // 알람 재생 X & 알람음 등록!
        if ( !this.isRunning && startId == 1) {
            MyDebug.log("ON SERVICE ON START COMMAND 1 실행 : + " + videoId);
            startVideo(videoId);
            this.isRunning = true;
            this.startId= 0;
            MyDebug.log("ON SERVICE ON START COMMAND 1 실행 완료 : + " + videoId);
        }

        // 알람 재생 O & 알람음 종료!
        else if (this.isRunning && startId == 0) {
            MyDebug.log("ON SERVICE ON START COMMAND 2 실행 : + " + videoId);
            this.isRunning = false;
            this.startId = 0;
            MyDebug.log("ON SERVICE ON START COMMAND 2 실행 완료: + " + videoId);

        }

        // 알람 재생 O & 알람음 종료!
        else if (!this.isRunning && startId == 0) {
            MyDebug.log("ON SERVICE ON START COMMAND 3 실행 완료: + " + videoId);
            this.isRunning = false;
            this.startId = 0;
            MyDebug.log("ON SERVICE ON START COMMAND 3 실행 완료: + " + videoId);
        }

        else if (this.isRunning && startId == 1) {
            MyDebug.log("ON SERVICE ON START COMMAND 4 실행 완료: + " + videoId);
            this.isRunning = true;
            this.startId = 1;
            MyDebug.log("ON SERVICE ON START COMMAND 4 실행 완료: + " + videoId);
        }

        else {
            MyDebug.log("ON SERVICE ON START COMMAND 5 실행 완료: + " + videoId);
        }

        return START_NOT_STICKY;
        //return super.onStartCommand(intent, flags, startId);
    }
    public void startVideo (String videoId) {
        Intent showIntent = new Intent(getApplicationContext(), YoutubeActivity.class);
        showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        showIntent.putExtra("videoId", videoId);
        startActivity(showIntent);
    }
}
