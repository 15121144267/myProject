package com.banshengyuan.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface FairProductDetailApi {
    @POST("member/deliveraddress")
    Observable<String> addAddressRequest(@Body String request);

    @GET("api/hot/{id}")
    Observable<String> hotFairDetailRequest(@Path("id") String id,@Query("id") String id2,@Query("flag") boolean flag);

    @POST("api/hot/{id}/sign-up/status")
    Observable<String> hotFairStateRequest(@Path("id") String id, @Body String request);

    @POST("api/hot/{id}/sign-up")
    Observable<String> hotFairJoinActionRequest(@Path("id") String id, @Body String request);

}
