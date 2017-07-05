package com.dispatching.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface AddAddressApi {
    @POST("member/deliveraddress")
    Observable<String> addAddressRequest(@Body String request);

}
