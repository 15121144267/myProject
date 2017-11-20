package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.network.networkapi.WelcomeApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class WelcomeModel {
    private final WelcomeApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public WelcomeModel(WelcomeApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }

    public Observable<ResponseData> personInfoRequest(String phone) {
        return mApi.personInfoRequest(BuildConfig.PARTNER_ID, phone).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> requestPic( ) {
        return mApi.requestPic().map(mTransform::transformCommon);
    }

}
