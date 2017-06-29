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

public interface SignApi {
    @GET("member/sms?")
    Observable<String> verityCodeRequest(@Query("partnerId") String partnerId, @Query("phone") String phone);

    @POST("member/register")
    Observable<String> signUpRequest(@Body String response);

}
