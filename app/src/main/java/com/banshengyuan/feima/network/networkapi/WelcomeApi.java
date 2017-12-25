package com.banshengyuan.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface WelcomeApi {

    @GET("api/banner")
    Observable<String> requestPic();

}
