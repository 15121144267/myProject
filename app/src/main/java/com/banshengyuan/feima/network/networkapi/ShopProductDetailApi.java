package com.banshengyuan.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface ShopProductDetailApi {
    @POST("member/deliveraddress")
    Observable<String> addAddressRequest(@Body String request);

}
