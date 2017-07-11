package com.example.hw.smartbutler.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by HW on 10/07/2017.
 * SharedPreferences封装 （数据存储工具类）
 */

public class ShareUtils {

    //测试方法: 存和取
//    private void test(Context mContext){
//        SharedPreferences sp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
//        sp.getString("key", "未获取到");
//
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString("key", "value");
//        editor.commit();
//    }

    public static  final String NAME = "config";

    // ------String---------

    //存 (键 值)
    public static void putString(Context mContext, String key, String value){
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    //取 (键 默认值)
    public static String getString(Context mContext, String key, String defValue){
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    // -------Int--------

    //存 (键 值)
    public static void putInt(Context mContext, String key, int value){
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).commit();
    }

    //取 (键 默认值)
    public static int getInt(Context mContext, String key, int defValue){
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }

    // -------Boolean--------

    //存 (键 值)
    public static void putBoolean(Context mContext, String key, boolean value){
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    //取 (键 默认值)
    public static boolean getBoolean(Context mContext, String key, boolean defValue){
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    // -------delete--------

    //删除单个
    public static void deleteShare(Context mContext, String key){
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }

    //删除全部
    public static void deleteAll(Context mContext){
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }
}
