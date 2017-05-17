package com.dispatching.feima.view.model;

import com.dispatching.feima.network.networkapi.WorkSummaryApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/5/17.
 */

public class WorkSummaryModel {
    private WorkSummaryApi mApi;
    private Gson mGson;
    private ModelTransform mTransform;

    @Inject
    public WorkSummaryModel(WorkSummaryApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }

    public Observable<ResponseData> AllOrderInfoRequest(String token, String version, String uId, String startTime, String endTime) {

        return mApi.AllOrderInfoRequest(token,version,uId,startTime,endTime).map(mTransform::transformCommon);
    }
}
