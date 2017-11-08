package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.network.networkapi.BlockApi;
import com.google.gson.Gson;

import javax.inject.Inject;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class BlockModel {
    private final BlockApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public BlockModel(BlockApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }

    /*public Observable<ResponseData> deleteAddressRequest(AddAddressRequest request) {
        request.partnerId = partnerId;
        return mApi.deleteAddressRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }*/

}
