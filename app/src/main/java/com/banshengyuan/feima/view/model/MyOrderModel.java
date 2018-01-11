package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.entity.BuProcessor;
import com.banshengyuan.feima.entity.OrderRequest;
import com.banshengyuan.feima.network.networkapi.MyOrderApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class MyOrderModel {
    private final MyOrderApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;
    private BuProcessor mBuProcessor;

    @Inject
    public MyOrderModel(MyOrderApi api, Gson gson, ModelTransform transform, BuProcessor buProcessor) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
        mBuProcessor = buProcessor;
    }


    public Observable<ResponseData> myOrderListRequest(int pageNo, int pageSize,String search_status,boolean flag,String token) {
        return mApi.orderListRequest(pageNo, pageSize, search_status,token).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> cancelOrderRequest(String order_sn,String token) {
        OrderRequest request = new OrderRequest();
        request.order_sn = order_sn;
        request.token = token;
        return mApi.cancelOrderRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> comfirmOrderRequest(String order_sn,String token) {
        OrderRequest request = new OrderRequest();
        request.order_sn = order_sn;
        request.token = token;
        return mApi.comfirmOrderRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }
    public Observable<ResponseData> remindSendGoodsRequest(String order_sn,String token) {
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

//    public Observable<ResponseData> orderStatusListRequest(Integer status, Integer pageNo, Integer pageSize) {
//        return mApi.orderStatusListRequest(mBuProcessor.getUserId(), status, pageNo, pageSize).map(mTransform::transformTypeTwo);
//    }
}
