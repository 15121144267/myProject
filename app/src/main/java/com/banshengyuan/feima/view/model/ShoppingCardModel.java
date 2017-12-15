package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.entity.BuProcessor;
import com.banshengyuan.feima.entity.ShoppingCardChangeNumberRequest;
import com.banshengyuan.feima.entity.ShoppingCardDeleteRequest;
import com.banshengyuan.feima.network.networkapi.AddShoppingCardApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class ShoppingCardModel {
    private final AddShoppingCardApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;
    private final BuProcessor mBuProcessor;

    @Inject
    public ShoppingCardModel(AddShoppingCardApi api, Gson gson, ModelTransform transform, BuProcessor buProcessor) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
        mBuProcessor = buProcessor;
    }


    public Observable<ResponseData> shoppingCardListRequest() {
        return mApi.shoppingCardListRequest(mBuProcessor.getUserToken()).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> deleteProductRequest(Integer productId) {
        ShoppingCardDeleteRequest request = new ShoppingCardDeleteRequest();
        request.goods_id = productId.toString();
        request.token = mBuProcessor.getUserToken();
        return mApi.deleteProductRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> changeProductNumberRequest(Integer productId, String sku, Integer number) {
        ShoppingCardChangeNumberRequest request = new ShoppingCardChangeNumberRequest();
        request.goods_id = productId.toString();
        request.goods_sku = sku;
        request.number = number;
        request.token = mBuProcessor.getUserToken();
        return mApi.changeProductNumberRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

}
