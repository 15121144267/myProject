package com.banshengyuan.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface BlockDetailApi {
    @GET("member/deliveraddress/list")
    Observable<String> addressListRequest(@Query("partnerId") String partnerId, @Query("phone") String phone);

    @HTTP(method = "DELETE", path = "member/deliveraddress", hasBody = true)
    Observable<String> deleteAddressRequest(@Body String request);

    @POST("member/deliveraddress")
    Observable<String> addressDefaultRequest(@Body String request);
}
