package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.entity.ShopListRequest;
import com.banshengyuan.feima.entity.ShopRequest;
import com.banshengyuan.feima.network.networkapi.ShopListApi;
import com.banshengyuan.feima.network.networkapi.ShopOtherListApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class ShopListModel {
    private final ShopListApi mApi;
    private final ShopOtherListApi mShopOtherListApi;
    private final Gson mGson;
    private final ModelTransform mTransform;
    private final String partnerId = BuildConfig.PARTNER_ID;

    @Inject
    public ShopListModel(ShopListApi api,ShopOtherListApi shopOtherListApi, Gson gson, ModelTransform transform) {
        mApi = api;
        mShopOtherListApi = shopOtherListApi;
        mGson = gson;
        mTransform = transform;
    }

    public Observable<ResponseData> shopListRequest(Integer pagerNo, Integer pagerSize) {
        ShopListRequest request = new ShopListRequest();
        request.partnerId = partnerId;
        request.pageNo = pagerNo;
        request.pageSize = pagerSize;
        return mApi.shopListRequest(mGson.toJson(request)).map(mTransform::transformTypeTwo);
    }

    public Observable<ResponseData> shopIdRequest(String storeCode,Integer type) {
        ShopRequest request = new ShopRequest();
        request.partnerId = partnerId;
        request.storeCode = storeCode;
        request.typeFlag = type;
        return mApi.shopIdRequest(mGson.toJson(request)).map(mTransform::transformTypeTwo);
    }

    public Observable<ResponseData> shopListBannerRequest(String partnerId ) {
        return mShopOtherListApi.shopListBannerRequest(partnerId).map(mTransform::transformTypeThree);
    }


}
