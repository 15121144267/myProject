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


    public Observable<ResponseData> hotFairDetailRequest(String id,boolean flag) {
        return mApi.hotFairDetailRequest(id,id,flag).map(mTransform::transformCommon);
    }


    public Observable<ResponseData> hotFairStateRequest(String id,HotFariStateRequest hotFariStateRequest) {
        return mApi.hotFairStateRequest(id,mGson.toJson(hotFariStateRequest)).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> hotFairJoinActionRequest(String id,HotFariJoinActionRequest hotFariJoinActionRequest) {
        return mApi.hotFairJoinActionRequest(id,mGson.toJson(hotFariJoinActionRequest)).map(mTransform::transformCommon);
    }

    /*public Observable<ResponseData> addAddressRequest(AddAddressRequest request) {
        request.partnerId = BuildConfig.PARTNER_ID;
        return mApi.addAddressRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }*/

}
