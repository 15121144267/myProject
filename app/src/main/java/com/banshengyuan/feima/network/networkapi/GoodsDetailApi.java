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

public interface GoodsDetailApi {
    @GET("api/goods/{id}")
    Observable<String> goodInfoRequest(@Path("id") Integer id,@Query("flag") boolean flag);

    @POST("api/collect")
    Observable<String> goodsCollectionRequest(@Body String request);

    @GET("Query/Product/ListBaseInfos")
    Observable<String> goodInfoSpecificationRequest(@Query("shopId") String shopId, @Query("productId") String request);

}
