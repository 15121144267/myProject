package com.dispatching.feima.view.model;

import com.dispatching.feima.entity.SignRequest;
import com.dispatching.feima.network.networkapi.SignApi;
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
    private final String partnerId = "a8bee0dd-09d1-4fa9-a9eb-80cb36d3d611";

    @Inject
    public SignModel(SignApi api, Gson gson, ModelTransform transform) {
        mSignApi = api;
        mGson = gson;
        mTransform = transform;
    }


    public Observable<ResponseData> verityCodeRequest(String phone) {
        return mSignApi.verityCodeRequest(partnerId, phone).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> veritySignUp(String phone, String password, String verityCode) {
        SignRequest request = new SignRequest();
        request.partnerId = partnerId;
        request.phone = phone;
        request.smsCode = verityCode;
        request.password = password;
        return mSignApi.signUpRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

}
