package com.dispatching.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface MyOrderApi {
    @GET("Query/Order/ListPageByUser")
    Observable<String> orderListRequest(@Query("pagenumber") Integer pageNumber,@Query("pagesize") Integer pageSize,@Query("userId") String userId);

}
