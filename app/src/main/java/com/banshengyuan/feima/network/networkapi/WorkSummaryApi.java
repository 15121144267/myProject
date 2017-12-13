package com.banshengyuan.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by helei on 2017/4/27.
 * WorkSummaryApi
 */

public interface WorkSummaryApi {

    @GET("api/fair/{id}")
    Observable<String> fairDetailRequest(@Path("id") String fairId);
}
