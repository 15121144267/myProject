package com.dispatching.feima.view.model;

import com.dispatching.feima.BuildConfig;
import com.dispatching.feima.entity.AddShoppingCardRequest;
import com.dispatching.feima.entity.BuProcessor;
import com.dispatching.feima.entity.ShopListResponse;
import com.dispatching.feima.network.networkapi.AddShoppingCardApi;
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
    private final AddShoppingCardApi mAddShoppingCardApi;
    private final Gson mGson;
    private final ModelTransform mTransform;
    private BuProcessor mBuProcessor;
    private final String partnerId = BuildConfig.PARTNER_ID+"_";

    @Inject
    public GoodsDetailModel(GoodsDetailApi api, AddShoppingCardApi addShoppingCardApi, Gson gson, ModelTransform transform, BuProcessor buProcessor) {
        mApi = api;
        mAddShoppingCardApi = addShoppingCardApi;
        mGson = gson;
        mTransform = transform;
        mBuProcessor = buProcessor;
    }


    public Observable<ResponseData> goodInfoRequest(String productId) {
        return mApi.goodInfoRequest(productId).map(mTransform::transformTypeTwo);
    }

    public Observable<ResponseData> goodInfoSpecificationRequest(String productId) {
        ShopListResponse.ListBean mBean = mBuProcessor.getShopInfo();
        return mApi.goodInfoSpecificationRequest(partnerId + mBean.storeCode, productId).map(mTransform::transformTypeTwo);
    }

    public Observable<ResponseData> requestAddShoppingCard(AddShoppingCardRequest request) {
        ShopListResponse.ListBean mBean = mBuProcessor.getShopInfo();
        request.linkId = partnerId + mBean.storeCode;
        return mAddShoppingCardApi.requestAddShoppingCard(mGson.toJson(request)).map(mTransform::transformTypeTwo);
    }

}
