package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.entity.BuProcessor;
import com.banshengyuan.feima.entity.OrderConfirmedResponse;
import com.banshengyuan.feima.entity.PayRequest;
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

    public Observable<ResponseData> payRequest(OrderConfirmedResponse response, Integer payType, Integer channel) {
        PayRequest request = new PayRequest();
        request.order_sn = response.order_sn;
        request.payment_type = payType;
        request.type = channel;
        request.token = mBuProcessor.getUserToken();
        return mApi.payRequest(mGson.toJson(request)).map(mTransform::transformTypeFour);
    }

    public Observable<ResponseData> couponListRequest(String storeId, String status) {
        return mApi.couponListRequest(mBuProcessor.getUserToken(), storeId, status).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> payConfirmRequest(String storeId, String amount, String discount, String payed) {
        UnderPayRequest request = new UnderPayRequest();
        request.store_id = storeId;
        request.amount = amount;
        request.discount = discount;
        request.payed = payed;
        request.token = mBuProcessor.getUserToken();
        return mApi.payConfirmRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }


}
