package com.banshengyuan.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface LoginApi {
    @GET("member/info")
    Observable<String> personInfoRequest(@Query("partnerId") String partnerId, @Query("phone") String phone);

    @GET("member/login?")
    Observable<String> loginRequest(@Query("partnerId") String partnerId,@Query("phone") String phone,@Query("password") String password);
}
