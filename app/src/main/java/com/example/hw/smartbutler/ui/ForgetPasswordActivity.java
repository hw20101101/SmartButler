package com.example.hw.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hw.smartbutler.R;
import com.example.hw.smartbutler.entity.MyUser;
import com.example.hw.smartbutler.utils.HWLog;
import com.example.hw.smartbutler.utils.StaticClass;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by HW on 11/07/2017.
 * 忘记密码/修改密码
 */

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_email, et_oldPassword, et_newPassword, et_againPassword;
    private Button btn_forget_sure, btn_change_sure;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        initView();
    }

    private void initView(){
        et_email = (EditText) findViewById(R.id.et_email);
        et_oldPassword = (EditText) findViewById(R.id.et_old_password);
        et_newPassword = (EditText) findViewById(R.id.et_new_password);
        et_againPassword = (EditText) findViewById(R.id.et_again_password);

        //忘记密码的确认按钮
        btn_forget_sure = (Button) findViewById(R.id.btn_forget_sure);
        btn_forget_sure.setOnClickListener(this);

        //修改密码的确认按钮
        btn_change_sure = (Button) findViewById(R.id.btn_change_sure);
        btn_change_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_forget_sure:
                forgetPassword();
                break;

            case R.id.btn_change_sure:
                changePassword();
                break;
        }
    }

    //重置密码
    private void forgetPassword(){
        final String email = et_email.getText().toString().trim();
        if (!TextUtils.isEmpty(email)){

            //调用 Bmob 的重置密码方法
            BmobUser.resetPasswordByEmail(email, new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null){
                        Toast.makeText(ForgetPasswordActivity.this, "重置密码请求成功，请到" + email + "邮箱进行重置操作", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ForgetPasswordActivity.this, "重置密码请求失败:" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(this, StaticClass.EDIT_TEXT_NOT_NIL, Toast.LENGTH_SHORT).show();
        }
    }

    //修改密码
    private void changePassword(){
        String oldPassword = et_oldPassword.getText().toString().trim();
        String newPassword = et_newPassword.getText().toString().trim();
        String againPassword = et_againPassword.getText().toString().trim();

        if (!TextUtils.isEmpty(oldPassword) & !TextUtils.isEmpty(newPassword) & !TextUtils.isEmpty(againPassword)){

            if (newPassword.equals(againPassword)){

                //调用 Bmob 的修改密码方法
                BmobUser.updateCurrentUserPassword(oldPassword, newPassword, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null){
                            Toast.makeText(ForgetPasswordActivity.this, "修改密码成功", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(ForgetPasswordActivity.this, "修改密码失败:" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            } else {
                Toast.makeText(this, "两次输入的新密码不一致", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, StaticClass.EDIT_TEXT_NOT_NIL, Toast.LENGTH_SHORT).show();
        }
    }
}
