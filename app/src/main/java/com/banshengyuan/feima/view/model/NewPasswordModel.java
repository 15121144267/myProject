package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.entity.SetPasswordRequest;
import com.banshengyuan.feima.network.networkapi.NewPasswordApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class NewPasswordModel {
    private final NewPasswordApi mForgetApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public NewPasswordModel(NewPasswordApi api, Gson gson, ModelTransform transform) {
        mForgetApi = api;
        mGson = gson;
        mTransform = transform;
    }


    public Observable<ResponseData> setPasswordRequest(String phone, String smsCode, String password) {
        SetPasswordRequest request = new SetPasswordRequest();
        request.partnerId = BuildConfig.PARTNER_ID;
        request.phone = phone;
        request.smsCode = smsCode;
        request.password = password;
        return mForgetApi.setPasswordRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

}
