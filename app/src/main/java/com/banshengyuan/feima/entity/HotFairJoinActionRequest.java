package com.banshengyuan.feima.entity;

/**
 * Created by li.liu on 2018/1/10.
 */

public class HotFairJoinActionRequest {
    private String id;
    private String mobile;
    private String token;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
