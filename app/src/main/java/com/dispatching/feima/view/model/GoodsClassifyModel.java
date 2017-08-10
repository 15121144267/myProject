package com.dispatching.feima.view.model;

import com.dispatching.feima.network.networkapi.GoodsClassifyApi;
import com.google.gson.Gson;

import javax.inject.Inject;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class GoodsClassifyModel {
    private final GoodsClassifyApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public GoodsClassifyModel(GoodsClassifyApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }


    /*public Observable<ResponseData> addAddressRequest(AddAddressRequest request) {
        request.partnerId = BuildConfig.PARTNER_ID;
        return mApi.addAddressRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }*/

}
