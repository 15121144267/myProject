package com.banshengyuan.feima.entity;

import android.content.Context;
import android.text.TextUtils;

import com.banshengyuan.feima.utils.SharePreferenceUtil;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by helei on 2017/5/3.
 * BuProcessor
 */
@Singleton
public class BuProcessor {
    private LoginUser mLoginUser = new LoginUser();
    private final SharePreferenceUtil sharePreferenceUtil;
    private final Context mContext;

    @Inject
    public BuProcessor(Context arg1, SharePreferenceUtil arg2) {
        mContext = arg1;
        sharePreferenceUtil = arg2;
    }

    public boolean isValidLogin() {
        return mLoginUser != null && !TextUtils.isEmpty(mLoginUser.getUserToken()) && !TextUtils.isEmpty(mLoginUser.getUserPhone());
    }

    public String getUserToken() {
        if (!isValidLogin()) {
            return "";
        }
        return mLoginUser.getUserToken();
    }

    public String getUserPhone() {
        if (!isValidLogin()) {
            return "";
        }
        return mLoginUser.getUserPhone();
    }

    public LoginUser getLoginUser() {
        return mLoginUser;
    }

    public void setLoginUser(String userPhone, String userToken) {
        mLoginUser.setUserPhone(userPhone);
        mLoginUser.setUserToken(userToken);
        sharePreferenceUtil.setObjectValue(SpConstant.LOGIN_USER, mLoginUser);
    }

    public void reSetUserData() {
        // 恢复用户相关
        Object o1 = sharePreferenceUtil.getObjectValue(SpConstant.LOGIN_USER);
        if (o1 != null && o1 instanceof LoginUser) {
            mLoginUser = (LoginUser) o1;
        }
    }

    //退出登录清除数据
    public void clearLoginUser() {
        // 清空用户
        if (mLoginUser != null) {
            mLoginUser.clear();
        }
        sharePreferenceUtil.setObjectValue(SpConstant.LOGIN_USER, "");
    }

}
