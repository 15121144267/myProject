package com.banshengyuan.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface AddShoppingCardApi {

    @POST("api/car/add")
    Observable<String> requestAddShoppingCard(@Body String request);

    @GET("api/car/list")
    Observable<String> shoppingCardListRequest(@Query("token") String userToken);

    @POST("api/car/delete")
    Observable<String> deleteProductRequest(@Body String request);

    @POST("api/car/update-goods-number")
    Observable<String> changeProductNumberRequest(@Body String request);
}
