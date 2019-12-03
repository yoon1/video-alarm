package com.example.videoalarm.utils;

// Created by Sim on 2019-09-17.
// 디버깅 클래스 작성

import android.util.Log;

public class MyDebug {

    private static final String TAG = "AlarmManagerApp";

    public final static boolean DEBUG = true;

    public final static int LOG_EXCEPTION   = 1;
    public final static int LOG_ERROR       = 1 + LOG_EXCEPTION;
    public final static int LOG_WARNING     = 1 + LOG_ERROR;
    public final static int LOG_INFO        = 1 + LOG_WARNING;
    public final static int LOG_DEBUG       = 1 + LOG_INFO;

    private final static int LOG_LEVEL = LOG_DEBUG;

    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    public static void log(String message)
    {
        if (DEBUG)
        {
            String fullClassName = Thread.currentThread().getStackTrace()[3].getClassName();
            String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
            String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
            int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();

            Log.e(TAG, "[" + DateTimeUtil.getTimeWithMilliSec() + "] " + className + "." + methodName + "():" + lineNumber + ", " + message);
        }
    }
    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    public static void log(int logLevel, String message)
    {
        if(LOG_LEVEL >= logLevel)
        {
            String fullClassName = Thread.currentThread().getStackTrace()[3].getClassName();
            String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
            String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
            int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();

            Log.e(TAG, "[" + DateTimeUtil.getTimeWithMilliSec() + "] " + className + "." + methodName + "():" + lineNumber + ", " + message);
        }
    }
    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    public static void log(String tag, String message)
    {
        if (DEBUG)
        {
            String fullClassName = Thread.currentThread().getStackTrace()[3].getClassName();
            String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
            String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
            int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();

            Log.e(tag, "[" + DateTimeUtil.getTimeWithMilliSec() + "] " + className + "." + methodName + "():" + lineNumber + ", " + message);
        }
    }
    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
    public static void logHex(byte[] message)
    {
        if (DEBUG)
        {
            String fullClassName = Thread.currentThread().getStackTrace()[3].getClassName();
            String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
            String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
            int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();

            String strLog = String.format("LEN : %d, ", message.length);

            for( int idx = 0; idx < message.length; idx++ )
            {
                strLog += String.format("%02X ", (int)(message[idx] & 0xFF));
            }

            Log.e(TAG, className + "." + methodName + "():" + lineNumber + ", " + strLog);
        }
    }
    //--------------------------------------------------------------------------------------------//
    //
    //--------------------------------------------------------------------------------------------//
}
//------------------------------------------------------------------------------------------------//
//
//------------------------------------------------------------------------------------------------//
