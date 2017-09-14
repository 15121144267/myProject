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


    public Observable<ResponseData> sortListRequest(String shopId, String nodeId,Integer deep, String sortName, Integer sortOrder,Integer pageSize,Integer pageNumber) {
        return mApi.sortListRequest(shopId,nodeId,deep,sortName,sortOrder,pageSize,pageNumber).map(mTransform::transformTypeFour);
    }

}
