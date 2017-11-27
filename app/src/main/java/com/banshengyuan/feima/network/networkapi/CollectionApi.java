package com.banshengyuan.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * 我的收藏
 */

public interface CollectionApi {
    @POST("member/deliveraddress")
    Observable<String> addAddressRequest(@Body String request);

    @GET("api/user/collection/fair")
    Observable<String> collectionFairRequest(@Query("page") Integer page, @Query("pageSize") Integer pageSize ,@Query("flag") boolean flag,@Query("token") String token);

    @GET("api/user/collection/goods")
    Observable<String> collectionProductRequest(@Query("page") Integer page, @Query("pageSize") Integer pageSize ,@Query("flag") boolean flag,@Query("token") String token);

    @GET("api/user/collection/hot")
    Observable<String> collectionHotRequest(@Query("page") Integer page, @Query("pageSize") Integer pageSize ,@Query("flag") boolean flag,@Query("token") String token);

    @GET("api/user/collection/store")
    Observable<String> collectionShopRequest(@Query("page") Integer page, @Query("pageSize") Integer pageSize ,@Query("flag") boolean flag,@Query("token") String token);

    @GET("api/user/collection/street")
    Observable<String> collectionBlockRequest(@Query("page") Integer page, @Query("pageSize") Integer pageSize ,@Query("flag") boolean flag,@Query("token") String token);
}
