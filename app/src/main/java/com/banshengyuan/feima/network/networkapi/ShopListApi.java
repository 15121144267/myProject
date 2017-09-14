package com.banshengyuan.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface ShopListApi {
    @POST("base?op=getShopList")
    Observable<String> shopListRequest(@Body String request);

    @POST("base?op=getShop")
    Observable<String> shopIdRequest(@Body String request);
}
