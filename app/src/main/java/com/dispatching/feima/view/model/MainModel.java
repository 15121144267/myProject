package com.dispatching.feima.view.model;

import com.dispatching.feima.network.networkapi.MainApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 */

public class MainModel {
    private MainApi mMainApi;
    private Gson mGson;
    private ModelTransform mTransform;

    @Inject
    public MainModel(MainApi api, Gson gson, ModelTransform transform) {
        mMainApi = api;
        mGson = gson;
        mTransform = transform;
    }

    public Observable<ResponseData> OrderInfoRequest(String request) {
        return mMainApi.OrderInfoRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }
}
