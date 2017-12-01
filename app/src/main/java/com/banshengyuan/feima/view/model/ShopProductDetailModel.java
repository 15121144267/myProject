package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.entity.CollectionRequest;
import com.banshengyuan.feima.entity.CouponInfoRequest;
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
    public Observable<ResponseData> couponInfoRequest(Integer couponId) {
        CouponInfoRequest request = new CouponInfoRequest();
        request.flag = true;
        request.id = couponId+"";
        request.token = BuildConfig.USER_TOKEN;
        return mApi.couponInfoRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> collectionRequest(String id, String type) {
        CollectionRequest request = new CollectionRequest();
        request.id = id;
        request.type = type;
        request.flag = true;
        request.token = BuildConfig.USER_TOKEN;
        return mApi.collectionRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }
}
