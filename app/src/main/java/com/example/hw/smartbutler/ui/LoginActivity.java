package com.example.hw.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hw.smartbutler.MainActivity;
import com.example.hw.smartbutler.R;
import com.example.hw.smartbutler.entity.MyUser;
import com.example.hw.smartbutler.utils.HWLog;
import com.example.hw.smartbutler.utils.ShareUtils;
import com.example.hw.smartbutler.utils.StaticClass;
import com.example.hw.smartbutler.view.CustomDialog;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by HW on 11/07/2017.
 * 登录界面
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_login, btn_register, btn_forgetPassword;
    private EditText et_userName, et_password;
    private CheckBox keep_password;
    //进度条弹窗
    private CustomDialog progress_Dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView(){

        progress_Dialog = new CustomDialog(this, 200, 200, R.layout.dialog_progress, R.style.dialog_progress_style, Gravity.CENTER, R.style.pop_anim_style);
        //设置点击屏幕无效
        progress_Dialog.setCancelable(false);

        et_userName = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);//设置点击事件

        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);

        btn_forgetPassword = (Button) findViewById(R.id.btn_forgetPassword);
        btn_forgetPassword.setOnClickListener(this);

        //记住密码控件
        keep_password = (CheckBox) findViewById(R.id.keep_password);
        //设置选中的状态
        boolean isCheck = ShareUtils.getBoolean(this, StaticClass.KEEP_PASS_WORD, false);
        keep_password.setChecked(isCheck);

        if (isCheck){//获取保存的数据，并赋值
            et_userName.setText(ShareUtils.getString(this, StaticClass.USER_NAME, ""));
            et_password.setText(ShareUtils.getString(this, StaticClass.PASS_WORD, ""));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;

            case R.id.btn_login:
                handleLogin();
                break;
            case R.id.btn_forgetPassword:
                startActivity(new Intent(this, ForgetPasswordActivity.class));
                break;
        }
    }

    //处理登录逻辑
    private void handleLogin(){
        String userName = et_userName.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        if (!TextUtils.isEmpty(userName) & !TextUtils.isEmpty(password)){
            progress_Dialog.show();

            final MyUser user = new MyUser();
            user.setUsername(userName);
            user.setPassword(password);

            user.login(new SaveListener<MyUser>() {
                @Override
                public void done(MyUser myUser, BmobException e) {
                    progress_Dialog.dismiss();

                    if (e == null){
                        if (user.getEmailVerified()){//判断是否已验证邮箱
                            //跳转到主界面
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "请前往邮箱验证", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(LoginActivity.this, "登录失败:" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(this, StaticClass.EDIT_TEXT_NOT_NIL, Toast.LENGTH_SHORT).show();
        }
    }

    //Activity 销毁
    @Override
    protected void onDestroy() {
        super.onDestroy();

        //保存状态
        ShareUtils.putBoolean(this, StaticClass.KEEP_PASS_WORD, keep_password.isChecked());

        if (keep_password.isChecked()){//记住用户名和密码
            ShareUtils.putString(this, StaticClass.USER_NAME, et_userName.getText().toString().trim());
            ShareUtils.putString(this, StaticClass.PASS_WORD, et_password.getText().toString().trim());
        } else {//清除已保存的数据
            ShareUtils.deleteShare(this, StaticClass.USER_NAME);
            ShareUtils.deleteShare(this, StaticClass.PASS_WORD);
        }
    }
}
