package com.banshengyuan.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface ShopListApi {

    @POST("api/user/order/comment")
    Observable<String> publishCommentRequest(@Body String request,@Query("token") String token);

}
