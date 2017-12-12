package com.banshengyuan.feima.entity;


import java.io.Serializable;

/**
 * Created by bst on 2015/4/13.
 * 当前 app 的登陆用户
 */
public class LoginUser implements Serializable {
    private String userPhone;

    public String userToken;

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }


    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }


    public void clear() {
        userPhone = null;
        userToken = null;
    }
}
