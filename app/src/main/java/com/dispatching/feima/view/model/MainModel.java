package com.dispatching.feima.view.model;

import com.dispatching.feima.entity.MyOrderDeliveryData;
import com.dispatching.feima.entity.OrderDeliveryRequest;
import com.dispatching.feima.network.networkapi.MainApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 */

public class MainModel {
    private MainApi mMainApi;
    private Gson mGson;
    private ModelTransform mTransform;

    @Inject
    public MainModel(MainApi api, Gson gson, ModelTransform transform) {
        mMainApi = api;
        mGson = gson;
        mTransform = transform;
    }

    public Observable<ResponseData> OrderInfoRequest(Integer position,String token, String version, String uId) {
        MyOrderDeliveryData data = new MyOrderDeliveryData();
        data.uId = uId;
        OrderDeliveryRequest request = new OrderDeliveryRequest();
        request.token = token;
        request.version = version;
        request.data = data;
        switch (position){
            case 0:
                return mMainApi.WaitOrderInfoRequest(mGson.toJson(request)).map(mTransform::transformCommon);
            case 1:
                return mMainApi.SendingOrderInfoRequest(mGson.toJson(request)).map(mTransform::transformCommon);
            case 2:
                return mMainApi.CompletedOrderInfoRequest(mGson.toJson(request)).map(mTransform::transformCommon);
            default:
                ResponseData responseData = new ResponseData();
                return Observable.just(responseData);
        }
    }

}
