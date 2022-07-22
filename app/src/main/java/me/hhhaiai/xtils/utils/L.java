package me.hhhaiai.xtils.utils;

import android.util.Log;

public class L {
    private static final String TAG = "sanbo";

    public static void v(Throwable e) {
        v(Log.getStackTraceString(e));
    }

    public static void v(String info) {
        print(Log.VERBOSE, TAG, info);
    }


    public static void d(Throwable e) {
        d(Log.getStackTraceString(e));
    }

    public static void d(String info) {
        print(Log.DEBUG, TAG, info);
    }


    public static void i(Throwable e) {
        i(Log.getStackTraceString(e));
    }

    public static void i(String info) {
        print(Log.INFO, TAG, info);
    }

    public static void w(Throwable e) {
        w(Log.getStackTraceString(e));
    }

    public static void w(String info) {
        print(Log.WARN, TAG, info);
    }

    public static void e(Throwable e) {
        e(Log.getStackTraceString(e));
    }

    public static void e(String info) {
        print(Log.ERROR, TAG, info);
    }

    public static void print(int priority, String tag, String msg) {
        Log.println(priority, tag, msg);
    }

}
