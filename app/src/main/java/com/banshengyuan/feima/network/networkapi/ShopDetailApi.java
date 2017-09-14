package com.banshengyuan.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface ShopDetailApi {
    @GET("Query/Shop/ListAllProductsSort")
    Observable<String> shopGoodsListRequest(@Query("sortName") String sortName, @Query("sortOrder") Integer sortOrder, @Query("pagenumber") Integer number, @Query("pagesize") Integer pagesize, @Query("shopId") String shopId);
}
