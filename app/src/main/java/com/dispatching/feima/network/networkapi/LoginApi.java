package com.dispatching.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by helei on 2017/4/27.
 */

public interface LoginApi {
    @POST("delivery/login")
    Observable<String> verifyCodeRequest(@Body String request);

    @POST("delivery/login")
    Observable<String> loginRequest(@Body String request);
}
