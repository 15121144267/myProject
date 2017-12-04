package com.banshengyuan.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface ProductListApi {
    @GET("api/goods")
    Observable<String> productListRequest(@Query("category_id") Integer categoryId,@Query("page") Integer page,@Query("pageSize") Integer pageSize);

}
