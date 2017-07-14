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
import com.example.hw.smartbutler.adapter.LogisticsAdapter;
import com.example.hw.smartbutler.entity.LogisticsEntity;
import com.example.hw.smartbutler.utils.HWLog;
import com.example.hw.smartbutler.utils.StaticClass;
import com.example.hw.smartbutler.utils.UtilTools;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by HW on 13/07/2017.
 * 快递查询
 */

public class LogisticsActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_query;
    //物流信息列表
    private ListView lv_logisticsData;
    private EditText et_companyName, et_number;
    private List<LogisticsEntity> list = new ArrayList<>();

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
            public void onSuccess(String jsonData) {

                //4.解析返回的数据
                parsingJson(jsonData);
            }
        });
    }

    //解析返回的数据
    private void parsingJson(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject result = jsonObject.getJSONObject("result");
            JSONArray array = result.getJSONArray("list");

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = (JSONObject) array.get(i);

                //创建并设置实体类
                LogisticsEntity entity = new LogisticsEntity();
                entity.setRemark(object.getString("remark"));
                entity.setZone(object.getString("zone"));
                entity.setTime(object.getString("datetime"));
                list.add(entity);
            }

            //使数组倒序
            Collections.reverse(list);

            //设置ListView适配器
            LogisticsAdapter adapter = new LogisticsAdapter(this, list);
            lv_logisticsData.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
