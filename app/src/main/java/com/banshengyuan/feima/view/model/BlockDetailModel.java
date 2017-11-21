package com.banshengyuan.feima.view.model;

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

    @Inject
    public BlockDetailModel(BlockDetailApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }


    public Observable<ResponseData> blockDetailRequest(Integer blockId) {
        return mApi.blockDetailRequest(blockId,true).map(mTransform::transformCommon);
    }

}
