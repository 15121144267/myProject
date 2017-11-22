package com.banshengyuan.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface BrandFairApi {
    @GET("api/fair")
    Observable<String> fairListRequest(@Query("is_brand") String Brand_flag, @Query("page") Integer page, @Query("pageSize") Integer pageSize, @Query("flag") boolean flag);

}
