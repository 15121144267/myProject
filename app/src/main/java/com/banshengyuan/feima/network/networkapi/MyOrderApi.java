package com.banshengyuan.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface MyOrderApi {
    @GET("Query/Order/ListPageByUser")
    Observable<String> orderListRequest(@Query("pagenumber") Integer pageNumber, @Query("pagesize") Integer pageSize, @Query("userId") String userId);

    @GET("Query/Order/ListByUserStatus")
    Observable<String> orderStatusListRequest(@Query("userId") String userId, @Query("status") Integer status, @Query("pagenumber") Integer pagenumber, @Query("pagesize") Integer pagesize);

}
