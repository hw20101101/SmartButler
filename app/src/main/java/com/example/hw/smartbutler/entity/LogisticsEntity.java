package com.example.hw.smartbutler.entity;

/**
 * Created by HW on 14/07/2017.
 * 快速查询实体
 */

public class LogisticsEntity {

    private String time;
    private String remark;//快件信息
    private String zone;//地点

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}
