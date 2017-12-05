package com.banshengyuan.feima.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface AddShoppingCardApi {

    @POST("api/car/add")
    Observable<String> requestAddShoppingCard(@Body String request);

    @GET("Query/ShoppingCart/ListUserAllShoppingCarts")
    Observable<String> shoppingCardListRequest(@Query("userId") String userId,@Query("companyId") String companyId);

    @POST("ShoppingCart/DelProduct")
    Observable<String> deleteProductRequest(@Body String request);

    @POST("ShoppingCart/SetNumber")
    Observable<String> changeProductNumberRequest(@Body String request);
}
