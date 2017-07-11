package com.example.hw.smartbutler.utils;

import android.util.Log;

/**
 * Created by HW on 10/07/2017.
 * Log封装类
 */

public class HWLog {

    //开关
    public static final boolean DEBUG = true;
    //TAG
    public static final String TAG = "Smartbutler";

    //五个等级 D I W E F
    public static void d(String text){
        if (DEBUG){
            Log.d(TAG, text);
        }
    }

    public static void i(String text){
        if (DEBUG){
            Log.i(TAG, text);
        }
    }

    public static void w(String text){
        if (DEBUG){
            Log.w(TAG, text);
        }
    }

    public static void e(String text){
        if (DEBUG){
            Log.e(TAG, text);
        }
    }

//    public static void f(String text){
//        if (DEBUG){
//
//        }
//    }
}
