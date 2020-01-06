package com.example.videoalarm.utils;

// Created by
// 알람데이터를 관리하는 클래스를 작성한다

import com.example.videoalarm.models.Alarm;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class VideoAlarmManagerUtil {
    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    //싱글톤 객체
    private static VideoAlarmManagerUtil instance;

    // Parameters
    private ArrayList<Alarm> listAlarm = null;

    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    //싱글톤 객체를 얻기위한 함수
    public static VideoAlarmManagerUtil getInstance() {
        if (instance == null)
            instance = new VideoAlarmManagerUtil();
        return instance;
    }

    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    //생성자
    public VideoAlarmManagerUtil() {
        //TODO make init
        listAlarm = new ArrayList<>();
    }

    //--------------------------------------------------------------------------------------------//
    //
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
    //TODO make method
    //Good luck !
    public boolean AddAlarm(Alarm alarm) {
        SqlLiteUtil.getInstance().insert(alarm);
        return true;
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
    public boolean SetAlarm(Alarm alarm) { // alarm Setting
        if (alarm == null) return false;
        return true;
    }

    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    public boolean SetAlarmEnable(int alarmId) {
        SqlLiteUtil.getInstance().updateEnable(alarmId); // need update function
        return true;
    }

    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    public boolean DeleteAlarm(List<Integer> id) { /* delete alarm - ui */
        for (int i = 0; i < id.size(); i++) {
            SqlLiteUtil.getInstance().delete(id.get(i));
        }
        return true;
    }

    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    public boolean DeleteAlarm(String deleteText) { /* delete alarm - msg */
        if(CheckDeleteRegexp(deleteText)){
            String[] splitDeleteText = SplitText(deleteText);
            DeleteAlarm(splitDeleteText[1]);
        }
        return true;
    }

    //--------------------------------------------------------------------------------------------//
    // +++ 수정필요 : 보수작업이 필요/ 귀찮으니깐 쫌만 이따 ㅠ +++
    //--------------------------------------------------------------------------------------------//
    public boolean AddAlarm(String insertText) { /* add alarm - msg */
        if(CheckInsertRegexp(insertText)) {
            String[] splitInsertText = SplitText(insertText);
            //Alarm alarm = new Alarm(splitInsertText[0], splitInsertText[1], splitInsertText[2]);
            // SqlLiteUtil.getInstance().insert(alarm);
        } else {
            return false;
        }
        return true;
    }

    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    public boolean CheckInsertRegexp(String text) { /* insert regexp check */
        if (Pattern.matches("^in:.{1,80}:([1-9]|[01][0-9]|2[0-3])([0-5][0-9])([0-5][0-9]):[0-1]{7}$", text)) {
            return true;
        }
        return false;
    }

    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    public boolean CheckDeleteRegexp(String text) { /* delete regexp check */
        if (Pattern.matches("^in:.{1,80}$", text)) {
            return true;
        }
        return false;
    }

    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    public String CheckRegexp(String text) { /* search */
        if (text.contains("in:")) {
            return "in";
        } else if (text.contains("de:")) {
            return "de";
        }
        return null;
    }

    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    public String[] SplitText(String text) { /* split */
        String[] splitText = text.split("\\s*\\r?\\n\\s*");
        return splitText;
    }
}