package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.entity.BuProcessor;
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
        return mApi.orderListRequest(pageNo, pageSize, search_status,flag,token).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> cancelOrderRequest(String order_sn,String token) {
        return mApi.cancelOrderRequest(order_sn, token).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> comfirmOrderRequest(String order_sn,String token) {
        return mApi.comfirmOrderRequest(order_sn, token).map(mTransform::transformCommon);
    }
    public Observable<ResponseData> remindSendGoodsRequest(String order_sn,String token) {
        return mApi.remindSendGoodsRequest(order_sn, token).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> deleteOrderRequest(String order_sn,String token) {
        return mApi.deleteOrderRequest(order_sn, token).map(mTransform::transformCommon);
    }

//    public Observable<ResponseData> orderStatusListRequest(Integer status, Integer pageNo, Integer pageSize) {
//        return mApi.orderStatusListRequest(mBuProcessor.getUserId(), status, pageNo, pageSize).map(mTransform::transformTypeTwo);
//    }
}
