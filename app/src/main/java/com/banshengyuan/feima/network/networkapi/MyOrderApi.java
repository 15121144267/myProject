package com.banshengyuan.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface MyOrderApi {
    @GET("api/user/order/list")
    Observable<String> orderListRequest(@Query("page") Integer pageNumber, @Query("pageSize") Integer pageSize, @Query("search_status") String search_status, @Query("flag") boolean flag,@Query("token") String token);


    @GET("Query/Order/ListByUserStatus")
    Observable<String> orderStatusListRequest(@Query("userId") String userId, @Query("status") Integer status, @Query("pagenumber") Integer pagenumber, @Query("pagesize") Integer pagesize);

}
