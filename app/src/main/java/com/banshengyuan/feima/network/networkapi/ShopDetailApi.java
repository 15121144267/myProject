package com.banshengyuan.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface ShopDetailApi {
    @GET("api/goods/{id}/comment")
    Observable<String> goodsCommentRequest(@Path("id") String id, @Query("page") Integer page, @Query("pageSize") Integer pageSize);
}
