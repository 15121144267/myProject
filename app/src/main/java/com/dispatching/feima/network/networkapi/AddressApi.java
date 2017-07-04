package com.dispatching.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface AddressApi {
    @GET("member/deliveraddress/list")
    Observable<String> addressListRequest(@Query("partnerId") String partnerId, @Query("phone") String phone);

}
