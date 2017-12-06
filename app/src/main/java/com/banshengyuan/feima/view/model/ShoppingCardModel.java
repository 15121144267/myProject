package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.BuildConfig;
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

    @Inject
    public ShoppingCardModel(AddShoppingCardApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }


    public Observable<ResponseData> shoppingCardListRequest() {
        return mApi.shoppingCardListRequest(BuildConfig.USER_TOKEN).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> deleteProductRequest(Integer productId) {
        ShoppingCardDeleteRequest request = new ShoppingCardDeleteRequest();
        request.goods_id = productId.toString();
        request.token = BuildConfig.USER_TOKEN;
        return mApi.deleteProductRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> changeProductNumberRequest(Integer productId,String sku,Integer number) {
        ShoppingCardChangeNumberRequest request = new ShoppingCardChangeNumberRequest();
        request.goods_id = productId.toString();
        request.goods_sku = sku;
        request.number = number;
        request.token = BuildConfig.USER_TOKEN;
        return mApi.changeProductNumberRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

}
