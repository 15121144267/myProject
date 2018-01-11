package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.entity.OrderRequest;
import com.banshengyuan.feima.network.networkapi.OrderDetailApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class OrderDetailModel {
    private final OrderDetailApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public OrderDetailModel(OrderDetailApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }


    public Observable<ResponseData> orderDetailInfoRequest(String order_sn, String token) {
        return mApi.requestOrderDetailInfo(order_sn, order_sn, token).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> cancelOrderRequest(String order_sn, String token) {
        OrderRequest request = new OrderRequest();
        request.order_sn = order_sn;
        request.token = token;
        return mApi.cancelOrderRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> comfirmOrderRequest(String order_sn, String token) {
        OrderRequest request = new OrderRequest();
        request.order_sn = order_sn;
        request.token = token;
        return mApi.comfirmOrderRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }
    public Observable<ResponseData> remindSendGoodsRequest(String order_sn, String token) {
        OrderRequest request = new OrderRequest();
        request.order_sn = order_sn;
        request.token = token;
        return mApi.remindSendGoodsRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> deleteOrderRequest(String order_sn,String token) {
        OrderRequest request = new OrderRequest();
        request.order_sn = order_sn;
        request.token = token;
        return mApi.deleteOrderRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

}
