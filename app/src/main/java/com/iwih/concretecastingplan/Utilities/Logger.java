package com.iwih.concretecastingplan.Utilities;

import android.util.Log;

/**
 * Created by iwih on 11/09/2017.
 */

public class Logger {
    public static final String LOG_TAG = "ConcreteCastingPlan";

    public static void v(String msg, Throwable thr) {
        Log.v(LOG_TAG, msg, thr);
    }
        public static void v(String msg) {
        Log.v(LOG_TAG, msg);
    }


    public static void e(String msg, Throwable thr) {
        Log.e(LOG_TAG, msg, thr);
    }
    public static void e(String msg) {
        Log.e(LOG_TAG, msg);
    }

    public static void d(String msg, Throwable thr) {
        Log.d(LOG_TAG, msg, thr);
    }
    public static void d(String msg) {
        Log.d(LOG_TAG, msg);
    }

    public static void i(String msg, Throwable thr) {
        Log.i(LOG_TAG, msg, thr);
    }
    public static void i(String msg) {
        Log.i(LOG_TAG, msg);
    }

    public static void w(String msg, Throwable thr) {
        Log.w(LOG_TAG, msg, thr);
    }
    public static void w(String msg) {
        Log.w(LOG_TAG, msg);
    }
}
