package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.network.networkapi.SearchApi;
import com.banshengyuan.feima.network.networkapi.SearchOtherApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class SearchModel {
    private final SearchApi mApi;
    private final SearchOtherApi mSearchOtherApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public SearchModel(SearchApi api, SearchOtherApi searchOtherApi, Gson gson, ModelTransform transform) {
        mApi = api;
        mSearchOtherApi = searchOtherApi;
        mGson = gson;
        mTransform = transform;
    }


    public Observable<ResponseData> requestSearchList(String searchName, String searchType) {
        return mApi.requestSearchList(searchName, searchType).map(mTransform::transformCommon);
    }
}
