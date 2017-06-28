package com.dispatching.feima.view.model;

import com.dispatching.feima.network.networkapi.OrderDetailApi;
import com.google.gson.Gson;

import javax.inject.Inject;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class OrderDetailModel {
    private final OrderDetailApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public OrderDetailModel(OrderDetailApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }


   /* public Observable<ResponseData> LoginRequest(String phone, String password) {
        LoginRequest request = new LoginRequest();
        request.phone = phone;
        request.verifyCode = password;
        return mLoginApi.loginRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }*/

}
