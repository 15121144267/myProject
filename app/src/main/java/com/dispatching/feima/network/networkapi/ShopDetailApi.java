package com.dispatching.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface ShopDetailApi {
    @GET("Query/Shop/ListAllProducts")
    Observable<String> shopGoodsListRequest(@Query("pagenumber") Integer number,@Query("pagesize") Integer pagesize,@Query("shopId") String shopId);
}
