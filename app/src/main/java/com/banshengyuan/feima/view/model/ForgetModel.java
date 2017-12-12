package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.entity.ForgetRequest;
import com.banshengyuan.feima.network.networkapi.ForgetApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class ForgetModel {
    private final ForgetApi mForgetApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public ForgetModel(ForgetApi api, Gson gson, ModelTransform transform) {
        mForgetApi = api;
        mGson = gson;
        mTransform = transform;
    }


    public Observable<ResponseData> verityCodeRequest(String phone) {
        return mForgetApi.verityCodeRequest(phone).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> checkCodeRequest(String phone, String code, String password) {
        ForgetRequest request = new ForgetRequest();
        request.code = code;
        request.mobile = phone;
        request.password = password;
        return mForgetApi.checkCodeRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

}
