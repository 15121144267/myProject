package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.network.networkapi.ShopDetailApi;
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

    @Inject
    public ShopDetailModel(ShopDetailApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }


    public Observable<ResponseData> goodsCommentRequest(Integer goodsId, Integer page, Integer pagerSize) {
        return mApi.goodsCommentRequest(goodsId + "", page, pagerSize).map(mTransform::transformCommon);
    }


}
