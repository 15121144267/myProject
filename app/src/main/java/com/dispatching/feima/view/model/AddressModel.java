package com.dispatching.feima.view.model;

import com.dispatching.feima.entity.AddAddressRequest;
import com.dispatching.feima.network.networkapi.AddressApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class AddressModel {
    private final AddressApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;
    private final String partnerId = "a8bee0dd-09d1-4fa9-a9eb-80cb36d3d611";

    @Inject
    public AddressModel(AddressApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }


    public Observable<ResponseData> addressListRequest(String phone) {
        return mApi.addressListRequest(partnerId, phone).map(mTransform::transformTypeThree);
    }

    public Observable<ResponseData> deleteAddressRequest(AddAddressRequest request) {
        request.partnerId = partnerId;
        return mApi.deleteAddressRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

}
