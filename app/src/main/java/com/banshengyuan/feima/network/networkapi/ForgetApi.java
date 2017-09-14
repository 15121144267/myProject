package com.banshengyuan.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface ForgetApi {
    @GET("member/sms")
    Observable<String> verityCodeRequest(@Query("partnerId") String partnerId, @Query("phone") String phone);

    @GET("member/sms/verify")
    Observable<String> checkCodeRequest(@Query("partnerId") String partnerId, @Query("phone") String phone, @Query("smsCode") String code);
}
