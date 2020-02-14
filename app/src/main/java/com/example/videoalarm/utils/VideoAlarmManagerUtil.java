package com.example.videoalarm.utils;

// Created by
// 알람데이터를 관리하는 클래스를 작성한다

import android.app.AlarmManager;
import android.content.Intent;

import com.example.videoalarm.models.Alarm;

import java.util.ArrayList;
import java.util.List;

public class VideoAlarmManagerUtil {
    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    //싱글톤 객체
    private static VideoAlarmManagerUtil instance;

    // Parameters
    private ArrayList<Alarm> listAlarm = null;
    // alarmManager 추가.
    // private AlarmManager[] alarmManagers = new AlarmManager[5]; // size어떻게 잡지? 맨날 바뀌나..?
    private ArrayList<AlarmManager> alarmManagers = null;

    //--------------------------------------------------------------------------------------------//
    // 싱글톤 객체를 얻기위한 함수
    //--------------------------------------------------------------------------------------------//
    public static VideoAlarmManagerUtil getInstance() {
        if (instance == null)
            instance = new VideoAlarmManagerUtil();
        return instance;
    }

    //--------------------------------------------------------------------------------------------//
    // 생성자.
    //--------------------------------------------------------------------------------------------//
    public VideoAlarmManagerUtil() {
        //TODO make init
        listAlarm = new ArrayList<>();
        alarmManagers = new ArrayList<>();
    }

    //--------------------------------------------------------------------------------------------//
    // get AlarmList
    //--------------------------------------------------------------------------------------------//
    public ArrayList<Alarm> GetAlarmList() {
        //TODO make function
        return listAlarm;
    }

    //--------------------------------------------------------------------------------------------//
    // get AlarmManagerList
    //--------------------------------------------------------------------------------------------//
    public ArrayList<AlarmManager> GetAlarmManagerList() {
        return alarmManagers;
    }

    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    public Alarm GetAlarm(int alarmId) {
        //TODO make function
        MyDebug.log ("getalarm : " + alarmId ) ;
        for ( Alarm a : listAlarm) {
            MyDebug.log ("alarmId, getId : " + alarmId + "," + a.getId()) ;
            if ( alarmId == a.getId()) {
                return a;
            }
        }
        return null;
    }

    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    public boolean SetAlarmList() {
        //TODO make function
        listAlarm = SqlLiteUtil.getInstance().viewAlarmList();
        if(listAlarm == null) return false;
        return true;
    }

    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    public boolean SetAlarmManagerList() {
        return true;
    }

    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    public boolean SetAlarm(Alarm alarm) { // alarm Setting
        if (alarm == null) return false;
        return true;
    }



    //--------------------------------------------------------------------------------------------//
    // Insert Alarm
    //--------------------------------------------------------------------------------------------//
    //TODO make method
    //Good luck !
    public boolean AddAlarm(Alarm alarm) {
        SqlLiteUtil.getInstance().insert(alarm);
        listAlarm.add(alarm);
        return true;
    }



    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    /* public boolean DeleteAlarm(List<Integer> id) {
        ///delete alarm - ui
        for (int i = 0; i < id.size(); i++) {
            SqlLiteUtil.getInstance().delete(id.get(i));
        }
        return true;
    } */


    //--------------------------------------------------------------------------------------------//
    // Update Alarm
    //--------------------------------------------------------------------------------------------//
    public boolean UpdateAlarm(Alarm alarm) {
        SqlLiteUtil.getInstance().update(alarm);
        for ( Alarm tAlarm : listAlarm) {
            if ( tAlarm.getId() == alarm.getId())
                tAlarm = alarm;
        }
        return true;
    }

    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    public boolean UpdateAlarmEnable(int alarmId) {
        SqlLiteUtil.getInstance().updateEnable(alarmId); // need update function
        for ( Alarm alarm : listAlarm) {
            if ( alarm.getId() == alarmId)
                alarm.setEnable( alarm.getEnable() == 1 ? false : true);
        }
        return true;
    }

    //--------------------------------------------------------------------------------------------//
    // Update Alarm
    //--------------------------------------------------------------------------------------------//
    public boolean DeleteAlarm(int alarmId) {
        SqlLiteUtil.getInstance().delete(alarmId);
        for ( Alarm tAlarm : listAlarm) {
            if ( tAlarm.getId() == alarmId)
                listAlarm.remove(tAlarm);
        }
        return true;
    }
}