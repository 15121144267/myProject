package com.banshengyuan.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface ShopProductDetailApi {
    @GET("api/store/{id}")
    Observable<String> shopDetailRequest(@Path("id") Integer shopId);

    @GET("api/goods")
    Observable<String> storeProductListRequest(@Query("store_id") Integer shopId, @Query("page") Integer page, @Query("pageSize") Integer pageSize);

    @GET("api/store/{id}/ticket")
    Observable<String> storeCouponListRequest(@Path("id") Integer shopId);

    @POST("api/store/ticket/receive")
    Observable<String> couponInfoRequest(@Body String request);

    @POST("api/collect")
    Observable<String> collectionRequest(@Body String request);

}
