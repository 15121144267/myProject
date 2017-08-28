package com.dispatching.feima.view.model;

import com.dispatching.feima.network.networkapi.SearchApi;
import com.dispatching.feima.network.networkapi.SearchOtherApi;
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

    public Observable<ResponseData> requestProductList(String searchName, String partnerId, String sortName, Integer sortNO, Integer pagerSize, Integer pagerNo) {
        return mApi.requestProductList(searchName, partnerId, sortName, sortNO, pagerSize, pagerNo).map(mTransform::transformTypeTwo);
    }

    public Observable<ResponseData> requestShopList(String partnerId ,String searchName) {
        return mSearchOtherApi.requestShopList(partnerId, searchName).map(mTransform::transformTypeThree);
    }
}
