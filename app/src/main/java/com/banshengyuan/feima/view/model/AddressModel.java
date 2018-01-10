package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.entity.AddAddressRequest;
import com.banshengyuan.feima.network.networkapi.AddressApi;
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
    private final String partnerId = BuildConfig.PARTNER_ID;

    @Inject
    public AddressModel(AddressApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }

    public Observable<ResponseData> listAddressRequest(String token) {
        return mApi.listAddressRequest(token).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> deleteAddressRequest(String addressId, String token) {
        return mApi.deleteAddressRequest(addressId, token).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> updateAddressRequest(String addressId, AddAddressRequest request, String token) {
        return mApi.updateAddressRequest(addressId, mGson.toJson(request), token).map(mTransform::transformCommon);
    }


}
