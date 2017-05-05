package com.dispatching.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by helei on 2017/4/27.
 */

public interface MainApi {
    @POST("Delivery/Staff_GetWaitSendOrder")
    Observable<String> WaitOrderInfoRequest(@Body String request);

    @POST("Delivery/Staff_GetSendingOrder")
    Observable<String> SendingOrderInfoRequest(@Body String request);

    @POST("Delivery/Staff_GetSendedOrder")
    Observable<String> CompletedOrderInfoRequest(@Body String request);

    @POST("Delivery/Take")
    Observable<String> TakeDeliveryRequest(@Body String request);

    @POST("Delivery/Arrived")
    Observable<String> ArrivedDeliveryRequest(@Body String request);
}
