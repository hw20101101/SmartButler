package com.example.hw.smartbutler.entity;

import cn.bmob.v3.BmobUser;

/**
 * Created by HW on 11/07/2017.
 * 用户实体
 */

public class UserEntity extends BmobUser{

    private int age;
    private boolean sex;
    private String desc;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}