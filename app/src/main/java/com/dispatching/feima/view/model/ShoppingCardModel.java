package com.dispatching.feima.view.model;

import com.dispatching.feima.network.networkapi.AddShoppingCardApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class ShoppingCardModel {
    private final AddShoppingCardApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public ShoppingCardModel(AddShoppingCardApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }


    public Observable<ResponseData> shoppingCardListRequest(String companyId, String userId) {
        return mApi.shoppingCardListRequest(userId,companyId).map(mTransform::transformTypeFour);
    }

}
