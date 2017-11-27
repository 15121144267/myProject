package com.banshengyuan.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface UnderLineFairApi {
    @GET("api/street/{id}")
    Observable<String> blockDetailRequest(@Path("id") Integer blockId, @Query("flag") boolean flag);

    @GET("api/fair")
    Observable<String> blockFairListRequest(@Query("street_id") String street_id, @Query("page") Integer page, @Query("pageSize") Integer pageSize, @Query("flag") boolean flag);

    @GET("api/store")
    Observable<String> storeListRequest(@Query("street_id") Integer blockId, @Query("page") Integer page,@Query("pageSize") Integer pageSize,@Query("flag") boolean flag);
}
