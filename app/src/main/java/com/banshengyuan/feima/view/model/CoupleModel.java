package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.network.networkapi.CoupleApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class CoupleModel {
    private final CoupleApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public CoupleModel(CoupleApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }

    public Observable<ResponseData> myCoupleRequest(String states, int page, int pageSize, String token) {
        return mApi.myCoupleRequest(states, page , pageSize , token).map(mTransform::transformCommon);
    }
}
