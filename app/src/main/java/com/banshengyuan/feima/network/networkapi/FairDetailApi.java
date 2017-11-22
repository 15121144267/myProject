package com.banshengyuan.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface FairDetailApi {
    @GET("api/fair")
    Observable<String> fairListRequest(@Query("category_2_id") Integer fairId, @Query("page") Integer page, @Query("pageSize") Integer pageSize, @Query("flag") boolean flag);

    @GET("api/store")
    Observable<String> stroeListRequest(@Query("category_id") Integer fairId, @Query("page") Integer page, @Query("pageSize") Integer pageSize, @Query("flag") boolean flag);

    @GET("api/goods")
    Observable<String> productListRequest(@Query("category_id") Integer fairId, @Query("page") Integer page, @Query("pageSize") Integer pageSize, @Query("flag") boolean flag);

    @GET("api/fair/{id}/comment")
    Observable<String> commentListRequest(@Path("id") Integer fairId, @Query("page") Integer page, @Query("pageSize") Integer pageSize, @Query("flag") boolean flag);
}
