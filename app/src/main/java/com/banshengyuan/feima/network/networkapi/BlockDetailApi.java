package com.banshengyuan.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface BlockDetailApi {
    @GET("api/street/{id}")
    Observable<String> blockDetailRequest(@Path("id") Integer blockId, @Query("flag") boolean flag);



}
