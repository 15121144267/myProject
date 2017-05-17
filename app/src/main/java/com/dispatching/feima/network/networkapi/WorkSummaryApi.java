package com.dispatching.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 */

public interface WorkSummaryApi {

    @GET("api/Query/Staff/GetAllOrder")
    Observable<String> AllOrderInfoRequest(@Query("token") String token, @Query("version")String version,
                                           @Query("postman_id")String uId,@Query("start_time") String startTime,
                                           @Query("end_time")String endTime);
}
