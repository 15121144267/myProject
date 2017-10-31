package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.network.networkapi.BrandFairApi;
import com.google.gson.Gson;

import javax.inject.Inject;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class BrandFairModel {
    private final BrandFairApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public BrandFairModel(BrandFairApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }


   /* public Observable<ResponseData> addAddressRequest(AddAddressRequest request) {
        request.partnerId = BuildConfig.PARTNER_ID;
        return mApi.addAddressRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }*/

}
