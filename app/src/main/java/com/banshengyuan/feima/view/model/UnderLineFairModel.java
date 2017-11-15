package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.network.networkapi.UnderLineFairApi;
import com.google.gson.Gson;

import javax.inject.Inject;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class UnderLineFairModel {
    private final UnderLineFairApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public UnderLineFairModel(UnderLineFairApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }


    /*public Observable<ResponseData> addAddressRequest(AddAddressRequest request) {
        request.partnerId = BuildConfig.PARTNER_ID;
        return mApi.addAddressRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }*/

}
