package com.example.videoalarm.utils;

import com.example.videoalarm.models.Alarm;
import java.util.ArrayList;
public class VideoAlarmManagerUtil {

    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    private static VideoAlarmManagerUtil instance;

    // Parameters
    private ArrayList<Alarm> listAlarm = null;

    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    public static VideoAlarmManagerUtil getInstance() {
        if (instance == null)
            instance = new VideoAlarmManagerUtil();
        return instance;
    }

    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    public VideoAlarmManagerUtil() {
        //TODO make init
        listAlarm = new ArrayList<>();
    }

    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    public ArrayList<Alarm> GetAlarmList() {
        return listAlarm;
    }

    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    public Alarm GetAlarm(int alarmId) {
        for ( Alarm a : listAlarm) {
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
    public long  AddAlarm(Alarm alarm) {
        long result  = SqlLiteUtil.getInstance().insert(alarm);
        listAlarm.add(alarm);
        MyDebug.log("추가된 알람 result : " + result);
        return result;
    }

    //--------------------------------------------------------------------------------------------//
    //
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
        for ( Alarm tAlarm: listAlarm) {
            if ( tAlarm.getId() == alarmId){
                if( tAlarm.getEnable() == 1){
                    tAlarm.setEnable(false);
                }else {
                    tAlarm.setEnable(true);
                }
            }
       }
        return true;
    }

    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    public boolean DeleteAlarm(int alarmId) {

        ArrayList<Alarm> listRemove = new ArrayList<Alarm> ();
        for ( Alarm tAlarm : listAlarm) {
            if ( tAlarm.getId() == alarmId)
                listRemove.add(tAlarm);
        }
        listAlarm.removeAll(listRemove);
        SqlLiteUtil.getInstance().delete(alarmId);

        return true;
    }


}