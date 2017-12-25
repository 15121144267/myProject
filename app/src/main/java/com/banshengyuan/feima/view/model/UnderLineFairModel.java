package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.network.networkapi.UnderLineFairApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class UnderLineFairModel {
    private final UnderLineFairApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public UnderLineFairModel(UnderLineFairApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }


    public Observable<ResponseData> blockDetailRequest(Integer blockId) {
        return mApi.blockDetailRequest(blockId).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> blockFairListRequest(Integer blockId) {
        return mApi.blockFairListRequest(blockId + "", 1, 10).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> storeListRequest(Integer blockId) {
        return mApi.storeListRequest(blockId, 1, 10).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> productListRequest(Integer blockId) {
        return mApi.productListRequest(blockId, 1, 10).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> vistaListRequest(double longitude, double latitude) {
        return mApi.vistaListRequest(longitude + "", latitude + "", 1, 10).map(mTransform::transformCommon);
    }


}
