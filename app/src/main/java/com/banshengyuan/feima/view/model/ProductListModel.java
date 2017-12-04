package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.network.networkapi.ProductListApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class ProductListModel {
    private final ProductListApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public ProductListModel(ProductListApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }


    public Observable<ResponseData> productListRequest(Integer categoryId) {
        return mApi.productListRequest(categoryId,1,10).map(mTransform::transformCommon);
    }

}
