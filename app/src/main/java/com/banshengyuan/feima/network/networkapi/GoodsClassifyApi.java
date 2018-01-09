package com.banshengyuan.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface GoodsClassifyApi {
    @GET("api/fair/{id}/comment")
    Observable<String> commentListRequest(@Path("id") String fairId, @Query("page") Integer page, @Query("pageSize") Integer pageSize);

    @GET("api/all-fail-category")
    Observable<String> allFairListRequest();
}
