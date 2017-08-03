package com.dispatching.feima.view.model;

import com.dispatching.feima.entity.BuProcessor;
import com.dispatching.feima.entity.ShopListResponse;
import com.dispatching.feima.network.networkapi.GoodsDetailApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class GoodsDetailModel {
    private final GoodsDetailApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;
    private BuProcessor mBuProcessor;
    private ShopListResponse.ListBean mBean;
    private final String partnerId = "53c69e54-c788-495c-bed3-2dbfc6fd5c61_";
    @Inject
    public GoodsDetailModel(GoodsDetailApi api, Gson gson, ModelTransform transform, BuProcessor buProcessor) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
        mBuProcessor = buProcessor;
    }


    public Observable<ResponseData> goodInfoRequest(String productId) {
        return mApi.goodInfoRequest(productId).map(mTransform::transformTypeTwo);
    }

    public Observable<ResponseData> goodInfoSpecificationRequest(String productId) {
        mBean = mBuProcessor.getShopInfo();
        return mApi.goodInfoSpecificationRequest(partnerId + mBean.storeCode,productId).map(mTransform::transformTypeTwo);
    }

}
