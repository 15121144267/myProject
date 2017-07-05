package com.dispatching.feima.view.model;

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
    private final String partnerId = "a8bee0dd-09d1-4fa9-a9eb-80cb36d3d611";

    @Inject
    public AddAddressModel(AddAddressApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }


    public Observable<ResponseData> addAddressRequest(AddAddressRequest request) {
        request.partnerId = partnerId;
        return mApi.addAddressRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

}
