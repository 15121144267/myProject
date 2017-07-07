package com.dispatching.feima.view.model;

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
    private final String shopId = "178a14ba-85a8-40c7-9ff4-6418418f5a0c_31310040";

    @Inject
    public ShopDetailModel(ShopDetailApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }


    public Observable<ResponseData> shopGoodsListRequest(Integer pagerNumber,Integer pagerSize) {

        return mApi.shopGoodsListRequest(pagerNumber,pagerSize,shopId).map(mTransform::transformTypeTwo);
    }

}
