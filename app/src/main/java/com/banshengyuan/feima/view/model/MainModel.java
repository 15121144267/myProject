package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.network.networkapi.MainApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * MainModel
 */

public class MainModel {
    private final MainApi mMainApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public MainModel(MainApi api, Gson gson, ModelTransform transform) {
        mMainApi = api;
        mGson = gson;
        mTransform = transform;
    }

    public Observable<ResponseData> personInfoRequest(String token) {
        return mMainApi.personInfoRequest(token).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> exitLoginRequest(String token) {
        return mMainApi.exitLoginRequest(token).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> fairListRequest() {
        return mMainApi.fairListRequest().map(mTransform::transformCommon);
    }

    public Observable<ResponseData> productListRequest() {
        return mMainApi.productListRequest().map(mTransform::transformCommon);
    }

    public Observable<ResponseData> recommendTopRequest(double longitude, double latitude) {
        return mMainApi.recommendTopRequest(longitude + "", latitude + "").map(mTransform::transformCommon);
    }

    public Observable<ResponseData> requestRecommendBrand() {
        return mMainApi.recommendTopRequest("brand", 1, 5).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> requestRecommendBottom() {
        return mMainApi.recommendBottomRequest(2, 1, 10).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> requestFairBottom() {
        return mMainApi.requestFairBottom("read_count", 1, 10).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> vistaListRequest(double longitude, double latitude) {
        return mMainApi.vistaListRequest(longitude + "", latitude + "", 1, 10).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> musicListRequest() {
        return mMainApi.musicListRequest().map(mTransform::transformCommon);
    }

    public Observable<ResponseData> allProductSortRequest() {
        return mMainApi.allProductSortRequest(2, 1, 100).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> storeListRequest() {
        return mMainApi.storeListRequest().map(mTransform::transformCommon);
    }

    public Observable<ResponseData> hotFairRequest(int page, int pageSize, String street_id) {
        return mMainApi.hotFairRequest(page, pageSize, street_id).map(mTransform::transformCommon);
    }
}
