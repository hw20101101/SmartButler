package com.example.hw.smartbutler.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.hw.smartbutler.MainActivity;
import com.example.hw.smartbutler.R;
import com.example.hw.smartbutler.utils.HWLog;
import com.example.hw.smartbutler.utils.ShareUtils;
import com.example.hw.smartbutler.utils.StaticClass;
import com.example.hw.smartbutler.utils.UtilTools;

/**
 * Created by HW on 10/07/2017.
 * 闪屏页(首页)
 */

public class SplashActivity extends AppCompatActivity{

    /**
     * 1.延时2000ms
     * 2.判断程序是否是第一次运行
     * 3.自定义字体
     * 4.Activity全屏主题
     * */

    private TextView tv_splash;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case StaticClass.HANDLER_SPLASH:
                    //判断程序是否是第一次运行
                    if (isFirst()){
                        startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    }
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
    }

    private void initView(){

        //延时2000ms
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH, 2000);
        tv_splash = (TextView) findViewById(R.id.tv_splash);

        //设置字体
        UtilTools.setFont(this, tv_splash);
    }

    //判断程序是否是第一次运行
    private boolean isFirst(){
        boolean isFirst = ShareUtils.getBoolean(this, StaticClass.SHARE_IS_FIRST, true);
        if (isFirst){
            //标记用户已启动过APP
            ShareUtils.putBoolean(this, StaticClass.SHARE_IS_FIRST, false);
            return true;
        } else {
            return false;
        }
    }
}
