package com.banshengyuan.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface SearchApi {
    @GET("Query/Shop/ListProductByNameSort")
    Observable<String> requestProductList(@Query("productName") String searchName, @Query("shopId")String partnerId,
                                          @Query("sortName")String sortName, @Query("sortOrder")Integer sortNO, @Query("pagesize")Integer pagerSize, @Query("pagenumber")Integer pagerNo);

}
