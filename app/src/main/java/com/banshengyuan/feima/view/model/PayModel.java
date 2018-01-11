package com.banshengyuan.feima.view.model;

import android.content.Context;
import android.text.TextUtils;

import com.banshengyuan.feima.entity.BuProcessor;
import com.banshengyuan.feima.entity.OrderConfirmItem;
import com.banshengyuan.feima.entity.OrderConfirmedRequest;
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


    public Observable<ResponseData> orderConfirmedRequest(String addressId, List<OrderConfirmItem> list, Integer self) {
        OrderConfirmedRequest request = new OrderConfirmedRequest();
        if(!TextUtils.isEmpty(addressId)){
            request.address_id = Integer.valueOf(addressId);
        }
        request.detail = mGson.toJson(list);
        request.token = mBuProcessor.getUserToken();
        request.is_selffetch = self;
        return mApi.orderConfirmedRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> listAddressRequest(String token) {
        return mApi.listAddressRequest(token).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> couponListRequest(String storeId, String status) {
        return mApi.couponListRequest(mBuProcessor.getUserToken(), storeId, status).map(mTransform::transformCommon);
    }

}
