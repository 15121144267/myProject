package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.network.networkapi.ShopProductDetailApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class ShopProductDetailModel {
    private final ShopProductDetailApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public ShopProductDetailModel(ShopProductDetailApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }


    public Observable<ResponseData> shopDetailRequest(Integer shopId) {
        return mApi.shopDetailRequest(shopId,true).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> storeProductListRequest(Integer shopId) {
        return mApi.storeProductListRequest(shopId,true).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> storeCouponListRequest(Integer shopId) {
        return mApi.storeCouponListRequest(shopId,true).map(mTransform::transformCommon);
    }
}
