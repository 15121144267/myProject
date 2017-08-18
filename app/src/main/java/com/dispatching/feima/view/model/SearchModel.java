package com.dispatching.feima.view.model;

import com.dispatching.feima.network.networkapi.SearchApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class SearchModel {
    private final SearchApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public SearchModel(SearchApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }

    public Observable<ResponseData> requestProductList(String searchName, String partnerId, String sortName, Integer sortNO, Integer pagerSize, Integer pagerNo) {
        return mApi.requestProductList(searchName,partnerId,sortName,sortNO,pagerSize,pagerNo).map(mTransform::transformTypeTwo);
    }

}
