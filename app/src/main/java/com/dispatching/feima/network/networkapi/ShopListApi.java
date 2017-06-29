package com.dispatching.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface ShopListApi {
    @POST("delivery/login")
    Observable<String> verifyCodeRequest(@Body String request);

    @GET("member/login?")
    Observable<String> loginRequest(@Query("partnerId") String partnerId, @Query("phone") String phone, @Query("password") String password);
}
