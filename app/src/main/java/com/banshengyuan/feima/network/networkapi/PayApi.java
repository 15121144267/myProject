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

public interface PayApi {
    @POST("api/order/submit")
    Observable<String> orderConfirmedRequest(@Body String request);

    @POST("api/trade/payment")
    Observable<String> payRequest(@Body String request);

    @POST("Order/PayAccessOrders")
    Observable<String> updateOrderStatusRequest(@Body String request);

    @GET("api/user/receiving-address")
    Observable<String> listAddressRequest(@Query("token") String token);

    @GET("api/user/ticket")
    Observable<String> couponListRequest(@Query("token") String token,@Query("store_id") String storeId,@Query("status") String status);
}
