package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.BuildConfig;
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
    private final String partnerId = BuildConfig.PARTNER_ID;
    @Inject
    public ForgetModel(ForgetApi api, Gson gson, ModelTransform transform) {
        mForgetApi = api;
        mGson = gson;
        mTransform = transform;
    }


    public Observable<ResponseData> verityCodeRequest(String phone) {
        return mForgetApi.verityCodeRequest(partnerId, phone).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> checkCodeRequest(String phone ,String code) {
        return mForgetApi.checkCodeRequest(partnerId,phone, code).map(mTransform::transformCommon);
    }

}
