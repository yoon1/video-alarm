package com.example.videoalarm.utils;

// Created by Sim on 2019-09-17.


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {
    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    public static long getTimeMillisNow()
    {
        return System.currentTimeMillis();
    }
    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    public static String getTimeWithMilliSec()
    {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("HH:mm:ss.SSS", Locale.KOREA);
        Date currentTime = new Date( );
        String mTime = mSimpleDateFormat.format ( currentTime );

        return mTime;
    }
    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
}
