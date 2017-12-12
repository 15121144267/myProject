package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.entity.LoginRequest;
import com.banshengyuan.feima.network.networkapi.LoginApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class LoginModel {
    private final LoginApi mLoginApi;
    private final Gson mGson;
    private final ModelTransform mTransform;
    private final String partnerId = BuildConfig.PARTNER_ID;

    @Inject
    public LoginModel(LoginApi api, Gson gson, ModelTransform transform) {
        mLoginApi = api;
        mGson = gson;
        mTransform = transform;
    }


    public Observable<ResponseData> LoginRequest(String phone, String password) {
        LoginRequest request = new LoginRequest();
        request.mobile = phone;
        request.password = password;
        return mLoginApi.loginRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> personInfoRequest(String phone) {
        return mLoginApi.personInfoRequest(partnerId,phone).map(mTransform::transformCommon);
    }

}
