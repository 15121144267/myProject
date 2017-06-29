package com.dispatching.feima.view.model;

import com.dispatching.feima.network.networkapi.ShopListApi;
import com.google.gson.Gson;

import javax.inject.Inject;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class ShopListModel {
    private final ShopListApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;
    private final String partnerId = "a8bee0dd-09d1-4fa9-a9eb-80cb36d3d611";

    @Inject
    public ShopListModel(ShopListApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }


}
