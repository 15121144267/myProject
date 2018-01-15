package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.entity.BuProcessor;
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
    private final BuProcessor mBuProcessor;

    @Inject
    public ShopProductDetailModel(ShopProductDetailApi api, Gson gson, ModelTransform transform, BuProcessor buProcessor) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
        mBuProcessor = buProcessor;
    }


    public Observable<ResponseData> shopDetailRequest(Integer shopId) {
        return mApi.shopDetailRequest(shopId).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> storeProductListRequest(Integer shopId, Integer page, Integer pageSize) {
        return mApi.storeProductListRequest(shopId, page, pageSize).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> storeCouponListRequest(Integer shopId) {
        return mApi.storeCouponListRequest(shopId+"",mBuProcessor.getUserToken()).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> couponInfoRequest(Integer couponId) {
        CouponInfoRequest request = new CouponInfoRequest();
        request.id = couponId + "";
        request.token = mBuProcessor.getUserToken();
        return mApi.couponInfoRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> collectionRequest(String id, String type) {
        CollectionRequest request = new CollectionRequest();
        request.id = id;
        request.type = type;
        request.token = mBuProcessor.getUserToken();
        return mApi.collectionRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }
}
