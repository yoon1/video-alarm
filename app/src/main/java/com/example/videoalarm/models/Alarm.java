package com.example.videoalarm.models;

// Created by Sim on 2019-09-17.
// 알람에 필요한 데이터를 객체화 한다

public class Alarm {
    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    //Parameters, don't reference parameters directly, use setter and getter
    private int id = -1;
    private boolean enable = true; // 1 : true, 0 : false
    private String alarmDate = "";  //YYYYMMDD
    private String alarmTime = "";  //hhmmss
    private String alarmNote = "";  // 알람내용
    private int videoId = 1;        // 비디오이름
    private String videoName = "";  // 비디오명

    //--------------------------------------------------------------------------------------------//
    // Constructor
    //--------------------------------------------------------------------------------------------//

    public Alarm() {
    }

    public Alarm(int id, boolean enable, String alarmDate, String alarmTime, String alarmNote, int videoId, String videoName) {
        this.id = id;
        this.enable = enable;
        this.alarmDate = alarmDate;
        this.alarmTime = alarmTime;
        this.alarmNote = alarmNote;
        this.videoId = videoId;
        this.videoName = videoName;
    }

    //--------------------------------------------------------------------------------------------//
    // getter / setter
    //--------------------------------------------------------------------------------------------//

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEnable() {
        return enable;
    }

    public int getEnable() {
        if(enable) return 1;
        else return 0;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }


    public String getAlarmDate() {
        return alarmDate;
    }

    public void setAlarmDate(String alarmDate) {
        this.alarmDate = alarmDate;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getAlarmNote() {
        return alarmNote;
    }

    public void setAlarmNote(String alarmNote) {
        this.alarmNote = alarmNote;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }
}
