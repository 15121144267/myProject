package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.entity.Constant;
import com.banshengyuan.feima.network.networkapi.CollectionApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class CollectionModel {
    private final CollectionApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public CollectionModel(CollectionApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }


  /*  public Observable<ResponseData> addAddressRequest(AddAddressRequest request) {
        request.partnerId = BuildConfig.PARTNER_ID;
        return mApi.addAddressRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }*/

    public Observable<ResponseData> collectionFairRequest(Integer page, Integer pageSize, String token) {
        return mApi.collectionFairRequest(page, pageSize, true, token).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> collectionProduceRequest(Integer page, Integer pageSize, String token) {
        return mApi.collectionProductRequest(page, pageSize, true, token).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> collectionHotRequest(Integer page, Integer pageSize, String token) {
        return mApi.collectionHotRequest(page, pageSize, true, token).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> collectionShopRequest(Integer page, Integer pageSize, String token) {
        return mApi.collectionShopRequest(page, pageSize, true, token).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> collectionBlockRequest(Integer page, Integer pageSize, String token) {
        return mApi.collectionBlockRequest(page, pageSize, true, token).map(mTransform::transformCommon);
    }


}
