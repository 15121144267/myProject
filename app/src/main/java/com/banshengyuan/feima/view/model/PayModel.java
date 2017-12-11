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

import java.util.ArrayList;
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

    @Inject
    public PayModel(PayApi api, Gson gson, ModelTransform transform, BuProcessor buProcessor, Context context) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
        mContext = context;
    }


    public Observable<ResponseData> orderConfirmedRequest(String addressId, List<OrderConfirmItem> list) {
        OrderConfirmedRequest request = new OrderConfirmedRequest();
        request.address_id = addressId;
        request.detail = mGson.toJson(list);
        return mApi.orderConfirmedRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> payRequest(OrderConfirmedResponse response, String payCode) {
        PayRequest request = new PayRequest();
        List<PayRequest.OrdersBean> list = new ArrayList<>();
        for (String s : response.data) {
            PayRequest.OrdersBean order = new PayRequest.OrdersBean();
            order.orderId = s;
            order.pay_ebcode = payCode;
            list.add(order);
        }
        request.orders = list;
        return mApi.payRequest(mGson.toJson(request)).map(mTransform::transformTypeTwo);
    }

    public Observable<ResponseData> updateOrderStatusRequest(PayAccessRequest request) {
        return mApi.updateOrderStatusRequest(mGson.toJson(request)).map(mTransform::transformTypeTwo);
    }

    public Observable<ResponseData> listAddressRequest(String token) {
        return mApi.listAddressRequest(token).map(mTransform::transformCommon);
    }


}
