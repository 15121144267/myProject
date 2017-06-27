package com.dispatching.feima.view.model;

import com.dispatching.feima.network.networkapi.MyOrderApi;
import com.google.gson.Gson;

import javax.inject.Inject;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class MyOrderModel {
    private final MyOrderApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public MyOrderModel(MyOrderApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }


    /*public Observable<ResponseData> LoginRequest(String phone, String password) {
        LoginRequest request = new LoginRequest();
        request.phone = phone;
        request.verifyCode = password;
        return mSignApi.loginRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }*/

}
