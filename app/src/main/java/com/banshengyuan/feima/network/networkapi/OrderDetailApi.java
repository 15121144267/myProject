package com.banshengyuan.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface OrderDetailApi {
    @GET("api/user/order/{order_sn}")
    Observable<String> requestOrderDetailInfo(@Path("order_sn") String order_sn, @Query("order_sn") String order_sn1, @Query("token") String token);

    @POST("api/user/order/cancel")
    Observable<String> cancelOrderRequest(@Query("order_sn") String order_sn, @Query("token") String token);

    @POST("api/order/confirm-received")
    Observable<String> comfirmOrderRequest(@Query("order_sn") String order_sn, @Query("token") String token);

    @POST("api/order/remind")
    Observable<String> remindSendGoodsRequest(@Query("order_sn") String order_sn, @Query("token") String token);

    @POST("api/user/order/delete")
    Observable<String> deleteOrderRequest(@Query("order_sn") String order_sn, @Query("token") String token);
}
