package com.dispatching.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface SearchApi {
    @GET("member/info")
    Observable<String> personInfoRequest(@Query("partnerId") String partnerId, @Query("phone") String phone);

}
