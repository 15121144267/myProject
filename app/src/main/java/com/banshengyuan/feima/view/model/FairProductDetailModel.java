package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.entity.HotFariJoinActionRequest;
import com.banshengyuan.feima.entity.HotFariStateRequest;
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


    public Observable<ResponseData> hotFairDetailRequest(String id,String token) {
        return mApi.hotFairDetailRequest(id, id,token).map(mTransform::transformCommon);
    }


    public Observable<ResponseData> hotFairStateRequest(String id, String order_sn,String token) {
        return mApi.hotFairStateRequest(id, id,order_sn,token).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> hotFairJoinActionRequest(String id, String phone, String token) {
        return mApi.hotFairJoinActionRequest(id, id,phone, token).map(mTransform::transformCommon);
    }

    /*public Observable<ResponseData> addAddressRequest(AddAddressRequest request) {
        request.partnerId = BuildConfig.PARTNER_ID;
        return mApi.addAddressRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }*/

}
