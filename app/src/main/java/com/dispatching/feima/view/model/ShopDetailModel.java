package com.dispatching.feima.view.model;

import com.dispatching.feima.BuildConfig;
import com.dispatching.feima.network.networkapi.ShopDetailApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class ShopDetailModel {
    private final ShopDetailApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;
    private String partnerId = BuildConfig.PARTNER_ID+"_";
    private String shopId;

    @Inject
    public ShopDetailModel(ShopDetailApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }


    public Observable<ResponseData> shopGoodsListRequest(String sortName,Integer sortOrder,String storeCode, Integer pagerNumber, Integer pagerSize) {
        shopId = partnerId +storeCode;
        return mApi.shopGoodsListRequest(sortName, sortOrder,pagerNumber, pagerSize, shopId).map(mTransform::transformTypeTwo);
    }

}
