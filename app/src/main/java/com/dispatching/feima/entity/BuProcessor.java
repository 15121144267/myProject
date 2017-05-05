package com.dispatching.feima.entity;

import android.content.Context;

import com.dispatching.feima.utils.SharePreferenceUtil;

import javax.inject.Inject;

/**
 * Created by helei on 2017/5/3.
 */

public class BuProcessor {
    private Context mContext;
    private SharePreferenceUtil mSharePreferenceUtil;

    private String userToken;
    private String userId;
    @Inject
    public BuProcessor(Context context,SharePreferenceUtil sharePreferenceUtil){
        mContext = context;
        mSharePreferenceUtil = sharePreferenceUtil;
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
