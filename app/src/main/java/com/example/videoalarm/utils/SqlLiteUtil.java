package com.example.videoalarm.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.videoalarm.fragment.HomeFragment;
import com.example.videoalarm.models.Alarm;
import com.example.videoalarm.models.Defines;

import java.util.ArrayList;


public class SqlLiteUtil {
    private SQLiteDatabase sqLiteDatabase;
    private String tableName;

    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    private SqlLiteUtil() { }

    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    private static class SingleTon {
        public static final SqlLiteUtil Instance = new SqlLiteUtil();
    }

    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    public static SqlLiteUtil getInstance() {
        return SingleTon.Instance;
    }

    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    public void setInitView(Context context, String tableName) {
        this.tableName = tableName;
        DBHelper helper = new DBHelper(
                context,
                Defines.DATABASE_NAME,
                null,
                6);
        try {
            sqLiteDatabase = helper.getWritableDatabase();
        } catch (SQLiteException e) {
            e.printStackTrace();
            MyDebug.log(tableName + " == > CAN'T OPEN DATABASE ");
        }
    }

    //--------------------------------------------------------------------------------------------//
    // Alarm insert
    //--------------------------------------------------------------------------------------------//
    public long insert(Alarm alarm) {
        ContentValues values = new ContentValues();
        // 키,값의 쌍으로 데이터 입력
        if(tableName.equals(Defines.ALARM)) {
            values.put("enable", alarm.getEnable());
            values.put("alarmDate", alarm.getAlarmDate());
            values.put("alarmTime", alarm.getAlarmTime());
            values.put("alarmNote", alarm.getAlarmNote());
            values.put("videoId", alarm.getVideoId());
            //values.put("videoName", alarm.getVideoName());
            values.put("videoName", alarm.getVideoName());
        }

        long result = 0;
        try {
            result = sqLiteDatabase.insert(tableName, null, values);
            MyDebug.log(tableName+" : "+ result + "_ row insert SUCCESS");
        }catch(SQLiteException e) {
            e.printStackTrace();
            MyDebug.log(tableName+" : " + result + "_ row insert FAILURE.");
        }
        return result;
    }

    //--------------------------------------------------------------------------------------------//
    // Alarm update
    //--------------------------------------------------------------------------------------------//
    public void update(Alarm alarm) {
        ContentValues values = new ContentValues();
        int id = alarm.getId();
        // 키,값의 쌍으로 데이터 입력
        if(tableName.equals(Defines.ALARM)) {
            //values.put("enable", alarm.getEnable());
            values.put("alarmDate", alarm.getAlarmDate());
            values.put("alarmTime", alarm.getAlarmTime());
            values.put("alarmNote", alarm.getAlarmNote());
            values.put("videoId", alarm.getVideoId());
            values.put("videoName", alarm.getVideoName());
            //values.put("videoName", "video");
        }
        int result = sqLiteDatabase.update(tableName, // tableName
                values,    // 뭐라고 변경할지 ContentValues 설정
                "_id=" +id, // 바꿀 항목을 찾을 조건절
                null);// 바꿀 항목으로 찾을 값 String 배열

        MyDebug.log(tableName + "UPDATE :: " + result + "번째 row update 성공했음");
    }


    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    public void delete(int id) {
        int result = 0;
        try {
            result  = sqLiteDatabase.delete(tableName, "_id=?", new String[]{String.valueOf(id)});
            MyDebug.log(tableName +" :"+ result + " row delete SUCCESS , id = " + id);
        }catch(SQLiteException e) {
            e.printStackTrace();
            MyDebug.log(tableName +" : " + result + "row delete FAILURE. id = " + id);
        }
    }

    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    public ArrayList<Alarm> viewAlarmList() {
        Cursor c = sqLiteDatabase.query(tableName, null, null, null, null, null, null);
        ArrayList<Alarm> container = new ArrayList<>();
        try {
            if (c!= null) {
                while (c.moveToNext()) {
                    Alarm tAlarm = new Alarm();
                    tAlarm.setId(c.getInt(c.getColumnIndex("_id")));
                    tAlarm.setEnable((c.getInt(c.getColumnIndex("enable")) == 1));
                    tAlarm.setAlarmDate(c.getString(c.getColumnIndex("alarmDate")));
                    tAlarm.setAlarmTime(c.getString(c.getColumnIndex("alarmTime")));
                    tAlarm.setAlarmNote(c.getString(c.getColumnIndex("alarmNote")));
                    tAlarm.setVideoId(c.getString(c.getColumnIndex("videoId")));
                    tAlarm.setVideoName(c.getString(c.getColumnIndex("videoName")));
                    container.add(tAlarm);
                }
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
            MyDebug.log(tableName + " : SELECT FAILURE" );
        }
        return container;
    }

    //--------------------------------------------------------------------------------------------//
    // Alarm 반환.
    //--------------------------------------------------------------------------------------------//
    public Alarm selectAlarm (int id) {
        Cursor c = sqLiteDatabase.query(tableName, null, "_id=?", new String[]{String.valueOf(id)}, null, null, null);
        // sqlLiteDatabase = getReadableDatabase
        if ( c != null) MyDebug.log("NOT NULL");
        else MyDebug.log("NULL");
        Alarm tAlarm = new Alarm();
        c.moveToFirst();
        tAlarm.setEnable((c.getInt(c.getColumnIndex("enable")) == 1));
        tAlarm.setId(c.getInt(c.getColumnIndex("_id")));
        tAlarm.setEnable((c.getInt(c.getColumnIndex("enable")) == 1));
        tAlarm.setAlarmDate(c.getString(c.getColumnIndex("alarmDate")));
        tAlarm.setAlarmTime(c.getString(c.getColumnIndex("alarmTime")));
        tAlarm.setAlarmNote(c.getString(c.getColumnIndex("alarmNote")));
        tAlarm.setVideoId(c.getString(c.getColumnIndex("videoId")));
        tAlarm.setVideoName(c.getString(c.getColumnIndex("videoName")));
        return tAlarm;
    }

    //--------------------------------------------------------------------------------------------//
    // select Enable
    //--------------------------------------------------------------------------------------------//
    public boolean selectEnable (int id) {
        Cursor c = sqLiteDatabase.query(tableName, null, "_id=?", new String[]{String.valueOf(id)}, null, null, null);
        // sqlLiteDatabase = getReadableDatabase
        if ( c != null) MyDebug.log("NOT NULL");
        else MyDebug.log("NULL");
        Alarm tAlarm = new Alarm();
        c.moveToFirst();
        tAlarm.setEnable((c.getInt(c.getColumnIndex("enable")) == 1));
        return tAlarm.isEnable();
    }

    //--------------------------------------------------------------------------------------------//
    // update Enable
    //--------------------------------------------------------------------------------------------//
    public void updateEnable(int id) {
        ContentValues values = new ContentValues();
        boolean enable = selectEnable(id)?false:true;
        MyDebug.log("변경후 ENABLE : " + enable);
        values.put("enable", enable);
        int result = sqLiteDatabase.update(tableName, // tableName
                values,    // 뭐라고 변경할지 ContentValues 설정
                "_id=" +id, // 바꿀 항목을 찾을 조건절
                null);// 바꿀 항목으로 찾을 값 String 배열

        MyDebug.log(tableName + " updateEnable" + result + "번째 row update 성공했음");
    }

    //--------------------------------------------------------------------------------------------//
    // set Enable false
    //--------------------------------------------------------------------------------------------//
    public void setOffEnable(int id) {
        ContentValues values = new ContentValues();
        boolean enable = false;
        values.put("enable", enable);
        int result = sqLiteDatabase.update(tableName, // tableName
                values,    // 뭐라고 변경할지 ContentValues 설정
                "_id=" +id, // 바꿀 항목을 찾을 조건절
                null);// 바꿀 항목으로 찾을 값 String 배열
        MyDebug.log(tableName + " updateEnable" + result + "번째 row update 성공했음");
    }
}
