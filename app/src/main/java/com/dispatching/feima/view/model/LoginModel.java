package com.dispatching.feima.view.model;

import com.dispatching.feima.network.networkapi.LoginApi;
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
    private final String partnerId = "a8bee0dd-09d1-4fa9-a9eb-80cb36d3d611";

    @Inject
    public LoginModel(LoginApi api, Gson gson, ModelTransform transform) {
        mLoginApi = api;
        mGson = gson;
        mTransform = transform;
    }


    public Observable<ResponseData> LoginRequest(String phone, String password) {
       /* LoginRequest request = new LoginRequest();
        request.phone = phone;
        request.password = password*/
        return mLoginApi.loginRequest(partnerId,phone,password).map(mTransform::transformCommon);
    }

}
