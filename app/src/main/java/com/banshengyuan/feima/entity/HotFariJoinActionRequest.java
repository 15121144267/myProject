package com.banshengyuan.feima.entity;

/**
 * Created by lei.he on 2017/7/5.
 * AddAddressRequest
 */

public class HotFariJoinActionRequest {
    public String id;
    private String phone;
    public String token;
    public boolean flag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
}
