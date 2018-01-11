package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.entity.BuProcessor;
import com.banshengyuan.feima.entity.UnderPayRequest;
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
    public ReductionPayModel(ReductionPayApi api, Gson gson, ModelTransform transform, BuProcessor buProcessor) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
        mBuProcessor = buProcessor;
    }

    public Observable<ResponseData> couponListRequest(String storeId, String status) {
        return mApi.couponListRequest(mBuProcessor.getUserToken(), storeId, status).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> payConfirmRequest(Integer storeId, String amount, String discount,Integer couponId) {
        UnderPayRequest request = new UnderPayRequest();
        request.store_id = storeId;
        request.amount = amount;
        request.no_discount = discount;
        request.coupon_id = couponId;
        request.token = mBuProcessor.getUserToken();
        return mApi.payConfirmRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }


}
