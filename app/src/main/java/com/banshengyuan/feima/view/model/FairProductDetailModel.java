package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.entity.HotFairCollectionRequest;
import com.banshengyuan.feima.entity.HotFairJoinActionRequest;
import com.banshengyuan.feima.entity.HotFairStateRequest;
import com.banshengyuan.feima.network.networkapi.FairProductDetailApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class FairProductDetailModel {
    private final FairProductDetailApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public FairProductDetailModel(FairProductDetailApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }


    public Observable<ResponseData> hotFairDetailRequest(String id, String token) {
        return mApi.hotFairDetailRequest(id, id, token).map(mTransform::transformCommon);
    }


    public Observable<ResponseData> hotFairStateRequest(String id, String order_sn, String token) {
        HotFairStateRequest request = new HotFairStateRequest();
        request.setId(id);
        request.setOrder_sn(order_sn);
        request.setToken(token);
        return mApi.hotFairStateRequest(id, mGson.toJson(request)).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> hotFairJoinActionRequest(String id, String phone, String token) {
        HotFairJoinActionRequest request = new HotFairJoinActionRequest();
        request.setId(id);
        request.setMobile(phone);
        request.setToken(token);
        return mApi.hotFairJoinActionRequest(id, mGson.toJson(request)).map(mTransform::transformCommon);
    }

    //热闹收藏/取消收藏
    public Observable<ResponseData> hotFairCollectionRequest(String id, String token) {
        HotFairCollectionRequest request = new HotFairCollectionRequest();
        request.setId(id);
        request.setType("hot");
        request.setToken(token);
        return mApi.hotFairCollectionRequest(id, mGson.toJson(request)).map(mTransform::transformCommon);
    }

    /*public Observable<ResponseData> addAddressRequest(AddAddressRequest request) {
        request.partnerId = BuildConfig.PARTNER_ID;
        return mApi.addAddressRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }*/

}
