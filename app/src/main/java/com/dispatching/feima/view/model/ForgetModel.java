package com.dispatching.feima.view.model;

import com.dispatching.feima.network.networkapi.ForgetApi;
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
    private final String partnerId = "a8bee0dd-09d1-4fa9-a9eb-80cb36d3d611";
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
