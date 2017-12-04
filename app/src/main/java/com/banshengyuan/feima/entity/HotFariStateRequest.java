package com.banshengyuan.feima.entity;

/**
 * Created by lei.he on 2017/7/5.
 * AddAddressRequest
 */

public class HotFariStateRequest {
    public String id;
    public String token;
    public String order_sn;//报名订单号
    public boolean flag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }
}
