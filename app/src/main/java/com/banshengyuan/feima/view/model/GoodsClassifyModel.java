package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.network.networkapi.GoodsClassifyApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class GoodsClassifyModel {
    private final GoodsClassifyApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public GoodsClassifyModel(GoodsClassifyApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }


    public Observable<ResponseData> commentListRequest(Integer fairId, Integer page, Integer pageSize) {
        return mApi.commentListRequest(fairId + "", page, pageSize).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> allFairListRequest() {
        return mApi.allFairListRequest(true).map(mTransform::transformCommon);
    }
}
