package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.entity.BuProcessor;
import com.banshengyuan.feima.entity.PayRequest;
import com.banshengyuan.feima.network.networkapi.FinalPayApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class FinalPayModel {
    private final FinalPayApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;
    private final BuProcessor mBuProcessor;

    @Inject
    public FinalPayModel(FinalPayApi api, Gson gson, ModelTransform transform, BuProcessor buProcessor) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
        mBuProcessor = buProcessor;
    }

    public Observable<ResponseData> payRequest(String orderId, Integer payType, Integer orderType) {
        PayRequest request = new PayRequest();
        request.order_sn = orderId;
        request.payment_type = payType;
        request.type = orderType;
        request.token = mBuProcessor.getUserToken();
        return mApi.payRequest(mGson.toJson(request)).map(mTransform::transformTypeFour);
    }


}
