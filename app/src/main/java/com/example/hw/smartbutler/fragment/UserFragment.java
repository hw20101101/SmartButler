package com.example.hw.smartbutler.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hw.smartbutler.R;
import com.example.hw.smartbutler.entity.MyUser;
import com.example.hw.smartbutler.ui.LoginActivity;
import com.example.hw.smartbutler.utils.StaticClass;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by HW on 09/07/2017.
 * 个人中心
 */

public class UserFragment extends Fragment implements View.OnClickListener{

    private Button btn_change, btn_logout;
    private TextView tv_editData;
    private EditText et_username, et_age, et_sex, et_desc;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        btn_change = view.findViewById(R.id.btn_change);
        btn_change.setOnClickListener(this);

        btn_logout = view.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(this);

        tv_editData = view.findViewById(R.id.tv_editData);
        tv_editData.setOnClickListener(this);

        et_username = view.findViewById(R.id.et_username);
        et_sex = view.findViewById(R.id.et_sex);
        et_age = view.findViewById(R.id.et_age);
        et_desc = view.findViewById(R.id.et_desc);
        //设置控件不可编辑，也可以在xml中设置
        setEditTextEnabled(false);

        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        et_username.setText(user.getUsername());
        et_sex.setText(user.isSex() ? "男" : "女");
        et_age.setText(user.getAge() + "");
        et_desc.setText(user.getDesc());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_change:
                saveUserData();
                break;

            case R.id.btn_logout:
                userLogout();
                break;

            case R.id.tv_editData:
                setEditTextEnabled(true);
                btn_change.setVisibility(View.VISIBLE);
                break;
        }
    }

    //用户退出登录
    private void userLogout(){
        //清除缓存的用户对象
        MyUser.logOut();
        //使 currentUser 为 null
        BmobUser currentUser = MyUser.getCurrentUser();

        //跳转到登录界面
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

    //设置 EditText 的编辑状态
    private void setEditTextEnabled(boolean value){
        et_username.setEnabled(value);
        et_sex.setEnabled(value);
        et_age.setEnabled(value);
        et_desc.setEnabled(value);
    }

    //保存修改过的数据
    private void saveUserData(){
        String userName = et_username.getText().toString().trim();
        String sex = et_sex.getText().toString().trim();
        String age = et_age.getText().toString().trim();
        String desc = et_desc.getText().toString().trim();

        if (!TextUtils.isEmpty(userName)
                & !TextUtils.isEmpty(sex)
                & !TextUtils.isEmpty(age) ){

            MyUser user = new MyUser();
            user.setUsername(userName);
            user.setAge(Integer.parseInt(age));

            if (sex.equals("男")){
                user.setSex(true);
            } else {
                user.setSex(false);
            }

            if (TextUtils.isEmpty(desc)){
                user.setDesc(StaticClass.USER_DEDC_IS_NIL);
            } else {
                user.setDesc(desc);
            }

            BmobUser bmobUser = BmobUser.getCurrentUser();
            user.update(bmobUser.getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        setEditTextEnabled(false);
                        btn_change.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "修改失败：" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(getActivity(), StaticClass.EDIT_TEXT_NOT_NIL, Toast.LENGTH_SHORT).show();
        }
    }
}
