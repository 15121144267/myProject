package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.entity.ShopRequest;
import com.banshengyuan.feima.network.networkapi.ShopListApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class ShopListModel {
    private final ShopListApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public ShopListModel(ShopListApi api,Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }


    public Observable<ResponseData> shopIdRequest(String storeCode,Integer type) {
        ShopRequest request = new ShopRequest();
        return mApi.shopIdRequest(mGson.toJson(request)).map(mTransform::transformTypeTwo);
    }

    public Observable<ResponseData> publishCommentRequest(String gId, String content,String token) {
        ShopRequest request = new ShopRequest();
        return mApi.publishCommentRequest(gId,content,token).map(mTransform::transformTypeTwo);
    }


}
