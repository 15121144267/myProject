package com.banshengyuan.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface PersonCenterApi {
    @POST("api/user/modify")
    Observable<String> updatePersonInfoRequest(@Body String request);

}
