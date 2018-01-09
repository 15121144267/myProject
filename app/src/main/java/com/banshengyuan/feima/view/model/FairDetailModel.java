package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.network.networkapi.FairDetailApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class FairDetailModel {
    private final FairDetailApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public FairDetailModel(FairDetailApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }

    public Observable<ResponseData> fairListRequest(Integer fairId) {
        return mApi.fairListRequest(fairId,1,5).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> storeListRequest(Integer fairId) {
        return mApi.storeListRequest(fairId,1,5).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> productListRequest(Integer fairId) {
        return mApi.productListRequest(fairId,1,8).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> commentListRequest(Integer fairId) {
        return mApi.commentListRequest(fairId,1,5).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> categoryInfoRequest(Integer fairCategoryId) {
        return mApi.categoryInfoRequest(fairCategoryId).map(mTransform::transformCommon);
    }

}
