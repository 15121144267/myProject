package com.dispatching.feima.view.model;

import com.dispatching.feima.BuildConfig;
import com.dispatching.feima.entity.AddAddressRequest;
import com.dispatching.feima.network.networkapi.AddAddressApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class AddAddressModel {
    private final AddAddressApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public AddAddressModel(AddAddressApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }


    public Observable<ResponseData> addAddressRequest(AddAddressRequest request) {
        request.partnerId = BuildConfig.PARTNER_ID;
        return mApi.addAddressRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

}
