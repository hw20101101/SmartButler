package com.example.hw.smartbutler;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.hw.smartbutler.fragment.ButlerFragment;
import com.example.hw.smartbutler.fragment.GirlFragment;
import com.example.hw.smartbutler.fragment.UserFragment;
import com.example.hw.smartbutler.fragment.WechatFragment;
import com.example.hw.smartbutler.ui.SettingActivity;
import com.example.hw.smartbutler.utils.HWLog;
import com.example.hw.smartbutler.utils.ShareUtils;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<String> mTitle;
    private List<Fragment> mFragment;
    //悬浮窗
    private FloatingActionButton fab_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //去掉阴影
        getSupportActionBar().setElevation(0);

        initData();
        initView();

        //测试 Bugly SDK
//        CrashReport.testJavaCrash();

        //测试自己封装的数据存储工具类
//        ShareUtils.putString(this, "username", "刘冬冬");
//        ShareUtils.deleteAll(this);
//        String text = ShareUtils.getString(this, "username", "hw");
//        HWLog.d("text:" + text);

        //测试自己封装的日志类
//        HWLog.d("Test");
//        HWLog.i("Text");
//        HWLog.e("Text");
//        HWLog.w("Text");
    }

    private void initData(){

        //两个数组中元素的顺序要一致
        mTitle = new ArrayList<>();
        mTitle.add("服务管家");
        mTitle.add("微信精选");
        mTitle.add("美女社区");
        mTitle.add("个人中心");

        //两个数组中元素的顺序要一致
        mFragment = new ArrayList<>();
        mFragment.add(new ButlerFragment());
        mFragment.add(new WechatFragment());
        mFragment.add(new GirlFragment());
        mFragment.add(new UserFragment());
    }

    private void initView(){

        fab_setting = (FloatingActionButton) findViewById(R.id.fab_setting);
        fab_setting.setOnClickListener(this);
        //首页默认隐藏悬浮按钮
        fab_setting.setVisibility(View.GONE);

        mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);

        //预加载
        mViewPager.setOffscreenPageLimit(mFragment.size());

        //mViewPager滑动监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            //正在滑动
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //Page切换
            @Override
            public void onPageSelected(int position) {
                Log.i("TAG", "position:" + position);

                if (position == 0){
                    //当前页面是服务管家，隐藏悬浮按钮
                    fab_setting.setVisibility(View.GONE);
                } else {
                    fab_setting.setVisibility(View.VISIBLE);
                }
            }

            //滑动状态的切换
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //设置适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            //选中的item
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            //返回item的个数
            @Override
            public int getCount() {
                return mFragment.size();
            }

            //设置标题
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });

        //绑定
        mTabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }
    }
}
