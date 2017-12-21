package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.entity.GoodsCommentContentRequest;
import com.banshengyuan.feima.entity.GoodsCommentRequest;
import com.banshengyuan.feima.entity.ShopRequest;
import com.banshengyuan.feima.network.networkapi.ShopListApi;
import com.google.gson.Gson;

import java.util.List;

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

    public Observable<ResponseData> publishCommentRequest(List<GoodsCommentContentRequest> mList, String token) {
        GoodsCommentRequest request = new GoodsCommentRequest();
        request.data =  mGson.toJson(mList);
        return mApi.publishCommentRequest(mGson.toJson(request),token).map(mTransform::transformTypeTwo);
    }


}
