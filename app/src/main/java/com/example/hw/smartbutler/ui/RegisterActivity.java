package com.example.hw.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.hw.smartbutler.R;
import com.example.hw.smartbutler.entity.MyUser;
import com.example.hw.smartbutler.utils.StaticClass;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by HW on 11/07/2017.
 * 注册界面
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    private EditText et_username, et_age, et_desc, et_pass, et_password, et_email;
    private RadioGroup radio_Group;
    private Button btn_register;
    //性别
    private boolean isGender = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.hw.smartbutler.R.layout.activity_register);
        initView();
    }

    private void initView(){
        et_username = (EditText) findViewById(R.id.et_username);
        et_age = (EditText) findViewById(R.id.et_age);
        et_desc = (EditText) findViewById(R.id.et_desc);
        et_pass = (EditText) findViewById(R.id.et_pass);
        et_password = (EditText) findViewById(R.id.et_password);
        et_email = (EditText) findViewById(R.id.et_email);

        radio_Group = (RadioGroup)findViewById(R.id.radio_Group);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
    }

    //点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_register:
                getTextValue();
                break;
        }

    }

    //获取输入框的值，并处理空数据
    private void getTextValue(){

        //获取输入框的值
        String name = et_username.getText().toString().trim();
        String age = et_age.getText().toString().trim();
        String desc = et_desc.getText().toString().trim();
        String pass = et_pass.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String email = et_email.getText().toString().trim();


        //判断数据是否为空
        if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(age)
                & !TextUtils.isEmpty(pass)
                & !TextUtils.isEmpty(password)
                & !TextUtils.isEmpty(email)){

            //判断两次输入的密码是否一致
            if (pass.equals(password)){
                generateUser(name, age, desc, email, password);
            } else {
                Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
            }

        } else {//数据为空
            Toast.makeText(this, StaticClass.EDIT_TEXT_NOT_NIL, Toast.LENGTH_SHORT).show();
        }
    }

    //处理注册逻辑
    private void generateUser(String name, String age, String desc, String email, String password){

        //判断性别
        radio_Group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                if (checkedId == R.id.rb_boy) {
                    isGender = true;
                } else {
                    isGender = false;
                }
            }
        });

        //判断简介
        if (TextUtils.isEmpty(desc)){
            desc = StaticClass.USER_DEDC_IS_NIL;
        }

        //生成用户数据
        MyUser user = new MyUser();
        user.setUsername(name);
        user.setAge(Integer.parseInt(age));
        user.setDesc(desc);
        user.setPassword(password);
        user.setEmail(email);
        user.setSex(isGender);

        user.signUp(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if (e == null){
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "注册失败：" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
