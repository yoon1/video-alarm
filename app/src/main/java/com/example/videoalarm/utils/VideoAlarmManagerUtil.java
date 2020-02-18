package com.example.videoalarm.utils;

// Created by
// 알람데이터를 관리하는 클래스를 작성한다

import android.content.Context;
import com.example.videoalarm.models.Alarm;
import java.util.ArrayList;
public class VideoAlarmManagerUtil {

    //--------------------------------------------------------------------------------------------//
    //private
    //--------------------------------------------------------------------------------------------//
    //싱글톤 객체
    private static VideoAlarmManagerUtil instance;

    // Parameters
    private ArrayList<Alarm> listAlarm = null;

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
    }

    //--------------------------------------------------------------------------------------------//
    // get AlarmList
    //--------------------------------------------------------------------------------------------//
    public ArrayList<Alarm> GetAlarmList() {
        //TODO make function
        return listAlarm;
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
    // Insert Alarm
    //--------------------------------------------------------------------------------------------//
    //TODO make method
    //Good luck !
    public long  AddAlarm(Alarm alarm) {
        long result  = SqlLiteUtil.getInstance().insert(alarm);
        listAlarm.add(alarm);
        //registerAlarm(context, alarm);
        MyDebug.log("추가된 알람 result : " + result);
        return result;
    }

    //--------------------------------------------------------------------------------------------//
    // Update Alarm
    //--------------------------------------------------------------------------------------------//
    public boolean UpdateAlarm(Alarm alarm) {
        SqlLiteUtil.getInstance().update(alarm);
        for ( Alarm tAlarm : listAlarm) {
            if ( tAlarm.getId() == alarm.getId())
                tAlarm = alarm;
        }
        //unregisterAlarm(context, alarm.getId());
        return true;
    }

    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    public boolean UpdateAlarmEnable(int alarmId) {
        SqlLiteUtil.getInstance().updateEnable(alarmId); // need update function

        for ( Alarm tAlarm: listAlarm) {
            if ( tAlarm.getId() == alarmId){
                if( tAlarm.getEnable() == 1){
                    tAlarm.setEnable(false);
                    //unregisterAlarm(context, alarm.getId());
                }else {
                    tAlarm.setEnable(true);
                    //registerAlarm(context, alarm);
                }
            }
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
        //unregisterAlarm(context, alarmId);
        return true;
    }


}