package com.dispatching.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface GoodsClassifyApi {
    @GET("Query/Menu/ListNextLevelByNodeId")
    Observable<String> sortListRequest(@Query("shopId") String shopId, @Query("nodeId") String nodeId, @Query("deep") Integer deep, @Query("sortName") String sortName, @Query("sortOrder") Integer sortOrder);

}
