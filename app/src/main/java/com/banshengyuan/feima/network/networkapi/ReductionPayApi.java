package com.banshengyuan.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface ReductionPayApi {

    @GET("api/user/ticket")
    Observable<String> couponListRequest(@Query("token") String token,@Query("store_id") String storeId,@Query("status") String status);
}
