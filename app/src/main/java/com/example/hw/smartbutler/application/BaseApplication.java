package com.example.hw.smartbutler.application;

import android.app.Application;

import com.example.hw.smartbutler.utils.StaticClass;
import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;

/**
 * Created by HW on 09/07/2017.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化 Bugly SDK
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_ID, false);

        //初始化 Bmob SDK
        Bmob.initialize(this, StaticClass.BMOB_APP_ID);
    }
}
