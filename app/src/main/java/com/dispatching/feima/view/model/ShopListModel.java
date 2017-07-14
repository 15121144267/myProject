package com.dispatching.feima.view.model;

import com.dispatching.feima.entity.ShopListRequest;
import com.dispatching.feima.entity.ShopRequest;
import com.dispatching.feima.network.networkapi.ShopListApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class ShopListModel {
    private final ShopListApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;
    private final String partnerId = "53c69e54-c788-495c-bed3-2dbfc6fd5c61";

    @Inject
    public ShopListModel(ShopListApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }

    public Observable<ResponseData> shopListRequest(Integer pagerNo, Integer pagerSize) {
        ShopListRequest request = new ShopListRequest();
        request.partnerId = partnerId;
        request.pageNo = pagerNo;
        request.pageSize = pagerSize;
        return mApi.shopListRequest(mGson.toJson(request)).map(mTransform::transformTypeTwo);
    }

    public Observable<ResponseData> shopIdRequest(String storeCode,Integer type) {
        ShopRequest request = new ShopRequest();
        request.partnerId = partnerId;
        request.storeCode = storeCode;
        request.typeFlag = type;
        return mApi.shopIdRequest(mGson.toJson(request)).map(mTransform::transformTypeTwo);
    }
}
