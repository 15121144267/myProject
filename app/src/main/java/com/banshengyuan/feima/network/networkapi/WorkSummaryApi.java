package com.banshengyuan.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * WorkSummaryApi
 */

public interface WorkSummaryApi {

    @GET("api/fair/{id}")
    Observable<String> fairDetailRequest(@Path("id") String fairId, @Query("token") String token);

    @POST("api/collect")
    Observable<String> collectionRequest(@Body String request);

    @POST("api/fair/zan")
    Observable<String> fairPraiseRequest(@Body String request);
}
