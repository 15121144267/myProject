package com.banshengyuan.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface AddAddressApi {
    @POST("api/user/receiving-address/create")
    Observable<String> addAddressRequest(@Body String request,@Query("token") String token);

    @POST("api/user/receiving-address/update/{id}")
    Observable<String> updateAddressRequest(@Path("id") String addressId, @Body String request ,@Query("token") String token);
}
