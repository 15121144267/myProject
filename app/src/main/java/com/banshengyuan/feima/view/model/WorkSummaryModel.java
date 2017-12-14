package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.entity.BuProcessor;
import com.banshengyuan.feima.entity.CollectionRequest;
import com.banshengyuan.feima.entity.FairPraiseRequest;
import com.banshengyuan.feima.network.networkapi.WorkSummaryApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/5/17.
 * WorkSummaryModel
 */

public class WorkSummaryModel {
    private final WorkSummaryApi mApi;
    private final ModelTransform mTransform;
    private Gson mGson;
    private BuProcessor mBuProcessor;

    @Inject
    public WorkSummaryModel(WorkSummaryApi api, Gson gson, ModelTransform transform, BuProcessor buProcessor) {
        mApi = api;
        mTransform = transform;
        mGson = gson;
        mBuProcessor = buProcessor;
    }

    public Observable<ResponseData> fairDetailRequest(Integer fairId) {
        return mApi.fairDetailRequest(fairId + "").map(mTransform::transformCommon);
    }

    public Observable<ResponseData> goodCollectionRequest(Integer goodId) {
        CollectionRequest request = new CollectionRequest();
        request.id = goodId + "";
        request.type = "goods";
        request.token = mBuProcessor.getUserToken();
        return mApi.collectionRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> fairCollectionRequest(Integer fairId) {
        CollectionRequest request = new CollectionRequest();
        request.id = fairId + "";
        request.type = "fair";
        request.token = mBuProcessor.getUserToken();
        return mApi.collectionRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> fairPraiseRequest(Integer fairId) {
        FairPraiseRequest request = new FairPraiseRequest();
        request.id = fairId + "";
        request.token = mBuProcessor.getUserToken();
        return mApi.fairPraiseRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }


}
