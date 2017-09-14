package com.banshengyuan.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface SearchOtherApi {
    @GET("store/search")
    Observable<String> requestShopList(@Query("partnerId") String partnerId, @Query("storeName") String searchName);

}
