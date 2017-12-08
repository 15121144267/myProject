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

public interface AddressApi {

    @GET("api/user/receiving-address")
    Observable<String> listAddressRequest(@Query("token") String token);

    @POST("api/user/receiving-address/delete/{id}")
    Observable<String> deleteAddressRequest(@Path("id") String addressId, @Query("token") String token);

    @POST("api/user/receiving-address/update/{id}")
    Observable<String> updateAddressRequest(@Path("id") String addressId, @Body String request ,@Query("token") String token);
}
