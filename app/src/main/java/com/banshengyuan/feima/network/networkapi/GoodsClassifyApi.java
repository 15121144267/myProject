package com.banshengyuan.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface GoodsClassifyApi {
    @GET("all-fail-category")
    Observable<String> allFairListRequest(@Query("flag") boolean flag);
}
