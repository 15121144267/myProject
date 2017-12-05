package com.banshengyuan.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface ShopBlockApi {
    @POST("api/store-category/list")
    Observable<String> shopSortListRequest();

    @GET("api/store")
    Observable<String> shopListRequest(@Query("category_id") String categoryId,@Query("street_id") String streetId,@Query("page") Integer page,@Query("pageSize") Integer pageSize);

}
