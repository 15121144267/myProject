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

public interface CoupleApi {
    @GET("api/user/ticket")
    Observable<String> myCoupleRequest(@Query("status") String status, @Query("page") int page,@Query("pageSize") int pageSize,@Query("token") String token);

    @HTTP(method = "DELETE", path = "member/deliveraddress", hasBody = true)
    Observable<String> deleteAddressRequest(@Body String request);

    @POST("member/deliveraddress")
    Observable<String> addressDefaultRequest(@Body String request);
}
