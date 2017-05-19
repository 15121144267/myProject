package com.dispatching.feima.view.model;

import com.dispatching.feima.BuildConfig;
import com.dispatching.feima.network.networkapi.WorkSummaryApi;
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
    private final String version;
    @Inject
    public WorkSummaryModel(WorkSummaryApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mTransform = transform;
        version = BuildConfig.VERSION_NAME;
    }

    public Observable<ResponseData> AllOrderInfoRequest(String token, String uId, String startTime, String endTime) {

        return mApi.AllOrderInfoRequest(token,version,uId,startTime,endTime).map(mTransform::transformCommon);
    }
}
