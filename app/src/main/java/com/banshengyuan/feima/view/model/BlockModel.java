package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.network.networkapi.BlockApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class BlockModel {
    private final BlockApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public BlockModel(BlockApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }

    public Observable<ResponseData> hotListRequest(Integer blockId ) {
        return mApi.hotListRequest(blockId,1,5).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> fairListRequest(Integer blockId ) {
        return mApi.fairListRequest(blockId,1,5).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> storeListRequest(Integer blockId ) {
        return mApi.storeListRequest(blockId,1,5).map(mTransform::transformCommon);
    }
}
