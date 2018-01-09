package com.banshengyuan.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface SearchApi {
    @GET("api/search")
    Observable<String> requestSearchList(@Query("kw") String searchName, @Query("type") String searchType);

}
