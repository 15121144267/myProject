package com.dispatching.feima.entity;

import javax.inject.Inject;

/**
 * Created by helei on 2017/5/3.
 * BuProcessor
 */

public class BuProcessor {

    private String userToken;
    private String userId;

    @Inject
    public BuProcessor() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}
