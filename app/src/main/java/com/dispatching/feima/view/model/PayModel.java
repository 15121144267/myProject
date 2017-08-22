package com.dispatching.feima.view.model;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.dispatching.feima.BuildConfig;
import com.dispatching.feima.entity.BuProcessor;
import com.dispatching.feima.entity.OrderConfirmedResponse;
import com.dispatching.feima.entity.PayAccessRequest;
import com.dispatching.feima.entity.PayCreateRequest;
import com.dispatching.feima.entity.PayRequest;
import com.dispatching.feima.entity.ShopListResponse;
import com.dispatching.feima.network.networkapi.PayApi;
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
    private final BuProcessor mBuProcessor;
    private final Context mContext;
    private AMapLocation mLocationInfo;
    private final String partnerId = BuildConfig.PARTNER_ID + "_";
    private ShopListResponse.ListBean mBean;

    @Inject
    public PayModel(PayApi api, Gson gson, ModelTransform transform, BuProcessor buProcessor, Context context) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
        mBuProcessor = buProcessor;
        mContext = context;
    }


    public Observable<ResponseData> orderConfirmedRequest(PayCreateRequest request) {
        return mApi.orderConfirmedRequest(mGson.toJson(request)).map(mTransform::transformTypeFour);
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

}
