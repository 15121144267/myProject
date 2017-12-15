package com.banshengyuan.feima.view.model;

import android.content.Context;

import com.banshengyuan.feima.entity.BuProcessor;
import com.banshengyuan.feima.entity.OrderConfirmItem;
import com.banshengyuan.feima.entity.OrderConfirmedRequest;
import com.banshengyuan.feima.entity.OrderConfirmedResponse;
import com.banshengyuan.feima.entity.PayAccessRequest;
import com.banshengyuan.feima.entity.PayRequest;
import com.banshengyuan.feima.network.networkapi.PayApi;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class PayModel {
    private final PayApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;
    private final Context mContext;
    private final BuProcessor mBuProcessor;

    @Inject
    public PayModel(PayApi api, Gson gson, ModelTransform transform, BuProcessor buProcessor, Context context) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
        mContext = context;
        mBuProcessor = buProcessor;
    }


    public Observable<ResponseData> orderConfirmedRequest(String addressId, List<OrderConfirmItem> list) {
        OrderConfirmedRequest request = new OrderConfirmedRequest();
        request.address_id = addressId;
        request.detail = mGson.toJson(list);
        request.token = mBuProcessor.getUserToken();
        return mApi.orderConfirmedRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> payRequest(OrderConfirmedResponse response, Integer payType, Integer channel) {
        PayRequest request = new PayRequest();
        request.order_sn = response.order_sn;
        request.payment_type = payType;
        request.type = channel;
        request.token = mBuProcessor.getUserToken();
        return mApi.payRequest(mGson.toJson(request)).map(mTransform::transformTypeFour);
    }

    public Observable<ResponseData> updateOrderStatusRequest(PayAccessRequest request) {
        return mApi.updateOrderStatusRequest(mGson.toJson(request)).map(mTransform::transformTypeTwo);
    }

    public Observable<ResponseData> listAddressRequest(String token) {
        return mApi.listAddressRequest(token).map(mTransform::transformCommon);
    }

}
