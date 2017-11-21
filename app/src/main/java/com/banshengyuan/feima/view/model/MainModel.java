package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.gen.OrderNoticeDao;
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
    public MainModel(MainApi api, Gson gson, ModelTransform transform, OrderNoticeDao orderNoticeDao) {
        mMainApi = api;
        mGson = gson;
        mTransform = transform;
    }

    public Observable<ResponseData> personInfoRequest(String phone) {
        return mMainApi.personInfoRequest(BuildConfig.PARTNER_ID, phone).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> recommendTopRequest(double longitude, double latitude) {
        return mMainApi.recommendTopRequest(longitude + "", latitude + "", true).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> requestRecommendBrand() {
        return mMainApi.recommendTopRequest("brand", 1, 5, true).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> requestRecommendBottom() {
        return mMainApi.recommendBottomRequest(2, 1, 10, true).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> requestFairUnderLine() {
        return mMainApi.requestFairUnderLine(1, 10, true).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> requestFairBottom() {
        return mMainApi.requestFairBottom("read_count", 1, 10, true).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> vistaListRequest(double longitude, double latitude) {
        return mMainApi.vistaListRequest(longitude + "", latitude + "", 1, 10, true).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> musicListRequest() {
        return mMainApi.musicListRequest(true).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> allProductSortRequest() {
        return mMainApi.allProductSortRequest(true).map(mTransform::transformCommon);
    }

}
