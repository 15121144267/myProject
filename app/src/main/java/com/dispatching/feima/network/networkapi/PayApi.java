package com.dispatching.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface PayApi {
    @POST("Order/Create")
    Observable<String> orderConfirmedRequest(@Body String request);

    @POST("/Order/Pay")
    Observable<String> payRequest(@Body String request);

    @POST("/Order/PayAccess")
    Observable<String> updateOrderStatusRequest(@Body String request);

}
