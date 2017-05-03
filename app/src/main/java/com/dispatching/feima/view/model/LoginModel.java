package com.dispatching.feima.view.model;

import com.dispatching.feima.entity.LoginRequest;
import com.dispatching.feima.network.networkapi.LoginApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 */

public class LoginModel {
    private LoginApi mLoginApi;
    private Gson mGson;
    private ModelTransform mTransform;

    @Inject
    public LoginModel(LoginApi api, Gson gson, ModelTransform transform) {
        mLoginApi = api;
        mGson = gson;
        mTransform = transform;
    }

    public Observable<ResponseData> VerifyCodeRequest(String request) {
        return mLoginApi.verifyCodeRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> LoginRequest(String phone,String password) {
        LoginRequest request = new LoginRequest();
        request.phone =phone;
        request.verifyCode = password;
        return mLoginApi.loginRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }
}
