package com.banshengyuan.feima.view.model;

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
    @Inject
    public WorkSummaryModel(WorkSummaryApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mTransform = transform;
    }

    public Observable<ResponseData> fairDetailRequest(Integer fairId) {
        return mApi.fairDetailRequest(fairId+"").map(mTransform::transformCommon);
    }
}
