package com.banshengyuan.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by liliu on 2017/4/27.
 * LoginApi
 */

public interface NoticeApi {

    @GET("api/user/message")
    Observable<String> noticeListRequest(@Query("page") int page, @Query("pageSize") int pageSize, @Query("token") String token);

}
