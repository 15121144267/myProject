package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.entity.BuProcessor;
import com.banshengyuan.feima.entity.CollectionRequest;
import com.banshengyuan.feima.network.networkapi.BlockDetailApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class BlockDetailModel {
    private final BlockDetailApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;
    private final BuProcessor mBuProcessor;

    @Inject
    public BlockDetailModel(BlockDetailApi api, Gson gson, ModelTransform transform,BuProcessor buProcessor) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
        mBuProcessor = buProcessor;
    }


    public Observable<ResponseData> blockDetailRequest(Integer blockId) {
        return mApi.blockDetailRequest(blockId,true).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> hotListRequest(Integer blockId ) {
        return mApi.hotListRequest(blockId,1,3,true).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> fairListRequest(Integer blockId ) {
        return mApi.fairListRequest(blockId,1,3,true).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> storeListRequest(Integer blockId ) {
        return mApi.storeListRequest(blockId,1,3,true).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> streetCollectionRequest(String id, String type) {
        CollectionRequest request = new CollectionRequest();
        request.id = id;
        request.type = type;
        request.flag = true;
        request.token = mBuProcessor.getUserToken();
        return mApi.streetCollectionRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }
}
