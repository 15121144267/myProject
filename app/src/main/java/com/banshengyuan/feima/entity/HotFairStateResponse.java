package com.banshengyuan.feima.entity;

/**
 * Created by li.liu on 2017/11/21.
 * 热闹-报名订单状态查询
 */

public class HotFairStateResponse {
    /**
     * status : 2
     * 1 提交报名 2付款完成
     * qrcode : 二维码url
     */

    private String status;
    private String qrcode;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }
}
