package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.entity.BuProcessor;
import com.banshengyuan.feima.network.networkapi.ReductionPayApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class ReductionPayModel {
    private final ReductionPayApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;
    private final BuProcessor mBuProcessor;

    @Inject
    public ReductionPayModel(ReductionPayApi api, Gson gson, ModelTransform transform,BuProcessor buProcessor) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
        mBuProcessor = buProcessor;
    }


    public Observable<ResponseData> couponListRequest(String storeId, String status) {
        return mApi.couponListRequest(mBuProcessor.getUserToken(), storeId, status).map(mTransform::transformCommon);
    }


}
