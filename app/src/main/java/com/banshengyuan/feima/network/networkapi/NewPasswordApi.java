package com.banshengyuan.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface NewPasswordApi {
    @POST("api/user/change-password")
    Observable<String> setPasswordRequest(@Body String request);

}
