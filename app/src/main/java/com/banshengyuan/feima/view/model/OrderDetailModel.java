package com.banshengyuan.feima.view.model;

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
        return mApi.requestOrderDetailInfo(order_sn, order_sn, token).map(mTransform::transformTypeTwo);
    }

}
