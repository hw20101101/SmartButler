package com.example.hw.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hw.smartbutler.R;
import com.example.hw.smartbutler.utils.HWLog;
import com.example.hw.smartbutler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

/**
 * Created by HW on 13/07/2017.
 * 快递查询
 */

public class LogisticsActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_query;
    //物流信息列表
    private ListView lv_logisticsData;
    private EditText et_companyName, et_number;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics);

        initView();
    }

    private void initView() {
        btn_query = (Button) findViewById(R.id.btn_query);
        btn_query.setOnClickListener(this);

        et_number = (EditText) findViewById(R.id.et_number);
        et_companyName = (EditText) findViewById(R.id.et_companyName);

        lv_logisticsData = (ListView) findViewById(R.id.lv_logisticsData);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_query:
                queryLogistics();
                break;
        }
    }

    //查询快递
    private void queryLogistics() {
        //1.获取输入框的内容
        String companyName = et_companyName.getText().toString().trim();
        String number = et_number.getText().toString().trim();

        //2.判断数据是否为空
        if (TextUtils.isEmpty(companyName) & TextUtils.isEmpty(number)){
            Toast.makeText(this, StaticClass.EDIT_TEXT_NOT_NIL, Toast.LENGTH_SHORT).show();
            return;
        }

        //3.发送网络请求，获取快递数据
        String url = "http://v.juhe.cn/exp/index?key=" + StaticClass.JUHE_KUAIDI_APP_ID + "&com=" + companyName + "&no=" + number;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                Toast.makeText(LogisticsActivity.this, t, Toast.LENGTH_SHORT).show();
                HWLog.d("-->> json:" + t);
            }
        });

        //4.解析返回的数据

        //5.设置ListView适配器

        //6.实体类

        //7.展示数据
    }

}
