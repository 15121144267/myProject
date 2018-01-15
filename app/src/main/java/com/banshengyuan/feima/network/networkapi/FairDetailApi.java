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
    Observable<String> fairListRequest(@Query("category_2_id") Integer fairId, @Query("page") Integer page, @Query("pageSize") Integer pageSize);

    @GET("api/store")
    Observable<String> storeListRequest(@Query("category_id") Integer fairId, @Query("page") Integer page, @Query("pageSize") Integer pageSize);

    @GET("api/goods")
    Observable<String> productListRequest(@Query("fair_category_2_id") Integer fairId, @Query("page") Integer page, @Query("pageSize") Integer pageSize);

    @GET("api/fair/{id}/comment")
    Observable<String> commentListRequest(@Path("id") Integer fairId, @Query("page") Integer page, @Query("pageSize") Integer pageSize);

    @GET("api/fair-category/{id}")
    Observable<String> categoryInfoRequest(@Path("id") Integer id);
}
