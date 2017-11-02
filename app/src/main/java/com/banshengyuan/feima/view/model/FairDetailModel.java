package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.network.networkapi.FairDetailApi;
import com.google.gson.Gson;

import javax.inject.Inject;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class FairDetailModel {
    private final FairDetailApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;
    private final String partnerId = BuildConfig.PARTNER_ID;

    @Inject
    public FairDetailModel(FairDetailApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }

   /* public Observable<ResponseData> deleteAddressRequest(AddAddressRequest request) {
        request.partnerId = partnerId;
        return mApi.deleteAddressRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }*/

}
