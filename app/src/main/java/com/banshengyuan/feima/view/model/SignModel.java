package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.entity.SignRequest;
import com.banshengyuan.feima.network.networkapi.SignApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class SignModel {
    private final SignApi mSignApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public SignModel(SignApi api, Gson gson, ModelTransform transform) {
        mSignApi = api;
        mGson = gson;
        mTransform = transform;
    }


    public Observable<ResponseData> verityCodeRequest(String phone) {
        return mSignApi.verityCodeRequest(phone).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> veritySignUp(String phone, String password, String verityCode) {
        SignRequest request = new SignRequest();
        request.mobile = phone;
        request.code = verityCode;
        request.password = password;
        return mSignApi.signUpRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

}
