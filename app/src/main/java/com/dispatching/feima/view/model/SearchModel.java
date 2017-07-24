package com.dispatching.feima.view.model;

import com.dispatching.feima.BuildConfig;
import com.dispatching.feima.network.networkapi.SearchApi;
import com.google.gson.Gson;

import javax.inject.Inject;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class SearchModel {
    private final SearchApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;
    private final String partnerId = BuildConfig.PARTNER_ID;

    @Inject
    public SearchModel(SearchApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }

    /*public Observable<ResponseData> personInfoRequest(String phone) {
        return mApi.personInfoRequest(partnerId,phone).map(mTransform::transformCommon);
    }*/

}
