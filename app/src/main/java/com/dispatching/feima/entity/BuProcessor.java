package com.dispatching.feima.entity;

import javax.inject.Inject;

/**
 * Created by helei on 2017/5/3.
 * BuProcessor
 */

public class BuProcessor {

    private String userToken;
    private String userId;
    private String userPhone;

    @Inject
    public BuProcessor() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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
}
