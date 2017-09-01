package com.dispatching.feima.view.model;

import com.dispatching.feima.BuildConfig;
import com.dispatching.feima.network.networkapi.ShopDetailApi;
import com.dispatching.feima.network.networkapi.ShopDetailOtherApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class ShopDetailModel {
    private final ShopDetailApi mApi;
    private final ShopDetailOtherApi mShopDetailOtherApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public ShopDetailModel(ShopDetailApi api, ShopDetailOtherApi shopDetailOtherApi, Gson gson, ModelTransform transform) {
        mApi = api;
        mShopDetailOtherApi = shopDetailOtherApi;
        mGson = gson;
        mTransform = transform;
    }


    public Observable<ResponseData> shopGoodsListRequest(String sortName, Integer sortOrder, String storeCode, Integer pagerNumber, Integer pagerSize) {
        String shopId = BuildConfig.PARTNER_ID + "_" + storeCode;
        return mApi.shopGoodsListRequest(sortName, sortOrder, pagerNumber, pagerSize, shopId).map(mTransform::transformTypeTwo);
    }

    public Observable<ResponseData> shopBannerRequest(String sortName, String storeCode) {
        return mShopDetailOtherApi.shopBannerRequest(sortName, storeCode).map(mTransform::transformTypeThree);
    }

}
