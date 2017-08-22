package com.dispatching.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface PayApi {
    @POST("Order/CreateOrders")
    Observable<String> orderConfirmedRequest(@Body String request);

    @POST("/Order/PayOrders")
    Observable<String> payRequest(@Body String request);

    @POST("/Order/PayAccessOrders")
    Observable<String> updateOrderStatusRequest(@Body String request);

}
