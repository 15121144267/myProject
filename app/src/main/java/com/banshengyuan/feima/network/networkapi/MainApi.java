package com.banshengyuan.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * MainApi
 */

public interface MainApi {

    @GET("api/user/info")
    Observable<String> personInfoRequest(@Query("token") String token);

    @GET("api/magic-door")
    Observable<String> musicListRequest(@Query("flag") boolean flag);

    @GET("api/fair/other-choice")
    Observable<String> fairListRequest(@Query("flag") boolean flag);

    @GET("api/goods/choice")
    Observable<String> productListRequest(@Query("flag") boolean flag);

    @GET("api/store/choice")
    Observable<String> storeListRequest(@Query("flag") boolean flag);

    @GET("api/goods-category")
    Observable<String> allProductSortRequest(@Query("flag") boolean flag);

    @GET("api/nearest-street-one")
    Observable<String> recommendTopRequest(@Query("longitude") String partnerId, @Query("latitude") String latitude, @Query("flag") boolean flag);

    @GET("api/fair-category")
    Observable<String> recommendTopRequest(@Query("type") String type, @Query("page") Integer page, @Query("pageSize") Integer pageSize, @Query("flag") boolean flag);

    @GET("api/fair")
    Observable<String> recommendBottomRequest(@Query("is_recommend") Integer is_recommend, @Query("page") Integer page, @Query("pageSize") Integer pageSize, @Query("flag") boolean flag);

    @GET("api/fair-category")
    Observable<String> requestFairBottom(@Query("order_by") String orderBy, @Query("page") Integer page, @Query("pageSize") Integer pageSize, @Query("flag") boolean flag);

    @GET("api/street")
    Observable<String> vistaListRequest(@Query("longitude") String partnerId, @Query("latitude") String latitude, @Query("page") Integer page, @Query("pageSize") Integer pageSize, @Query("flag") boolean flag);

    @GET("api/hot")
    Observable<String> hotFairRequest(@Query("page") Integer page, @Query("pageSize") Integer pageSize, @Query("street_id") String streetId,@Query("flag") boolean flag);
}
