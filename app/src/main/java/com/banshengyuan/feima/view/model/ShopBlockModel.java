package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.network.networkapi.ShopBlockApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class ShopBlockModel {
    private final ShopBlockApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public ShopBlockModel(ShopBlockApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }

    public Observable<ResponseData> shopSortListRequest( ) {
        return mApi.shopSortListRequest().map(mTransform::transformCommon);
    }

    public Observable<ResponseData> shopListRequest(Integer streetId, Integer categoryId) {
        return mApi.shopListRequest(streetId+"",categoryId+"",1,10).map(mTransform::transformCommon);
    }

}
