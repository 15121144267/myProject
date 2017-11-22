package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.network.networkapi.BrandFairApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class BrandFairModel {
    private final BrandFairApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public BrandFairModel(BrandFairApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }

    public Observable<ResponseData> fairListRequest( ) {
        return mApi.fairListRequest("2",1,20,true).map(mTransform::transformCommon);
    }

}
