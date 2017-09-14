package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.entity.AddAddressRequest;
import com.banshengyuan.feima.entity.AddressResponse;
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


    public Observable<ResponseData> addressListRequest(String phone) {
        return mApi.addressListRequest(partnerId, phone).map(mTransform::transformTypeThree);
    }

    public Observable<ResponseData> addressDefaultRequest(AddressResponse.DataBean addressRequest) {
        AddAddressRequest request = new AddAddressRequest();
        request.receiverName = addressRequest.receiverName.toString();
        request.receiverPhone = addressRequest.receiverPhone;
        request.address = addressRequest.address;
        request.area = addressRequest.area;
        request.phone = addressRequest.phone;
        request.isDefault = 1;
        request.partnerId = partnerId;
        request.id = addressRequest.id;
        return mApi.addressDefaultRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> deleteAddressRequest(AddAddressRequest request) {
        request.partnerId = partnerId;
        return mApi.deleteAddressRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

}
