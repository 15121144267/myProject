package com.banshengyuan.feima.entity;

/**
 * Created by li.liu on 2018/1/10.
 */

public class HotFairStateRequest {
    private String id;
    private String order_sn;
    private String token;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
