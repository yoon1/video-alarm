package com.example.videoalarm.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.videoalarm.models.Defines;

public class DBHelper extends SQLiteOpenHelper {
    DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlStudent = "create table if not exists "+ Defines.ALARM +"(" +
                "_id integer primary key autoincrement," +
                "enable text, " +
                "alarmDate text, " +
                "alarmTime text, " +
                "videoId int, " +
                "videoName text, " +
                "dayCircle int);";
        db.execSQL(sqlStudent);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sqlStudent = "drop table if exists "+ Defines.ALARM +";";
        db.execSQL(sqlStudent);
        onCreate(db);
    }
}
