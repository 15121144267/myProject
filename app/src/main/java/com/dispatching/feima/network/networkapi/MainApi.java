package com.dispatching.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * MainApi
 */

public interface MainApi {
    @GET("api/Query/Staff/GetWaitTake")
    Observable<String> WaitOrderInfoRequest(@Query("token") String token, @Query("postman_id") String uId, @Query("version") String version);

    @GET("api/Query/Staff/GetSendingOrder")
    Observable<String> SendingOrderInfoRequest(@Query("token") String token, @Query("postman_id") String uId, @Query("version") String version);

    @GET("api/Query/Staff/GetSendedOrder")
    Observable<String> CompletedOrderInfoRequest(@Query("token") String token, @Query("postman_id") String uId, @Query("version") String version);

    @POST("api/Delivery/Take")
    Observable<String> TakeDeliveryRequest(@Body String request);

    @POST("api/Delivery/Arrived")
    Observable<String> ArrivedDeliveryRequest(@Body String request);
}
