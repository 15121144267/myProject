package com.dispatching.feima.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by helei on 2017/5/3.
 */

public class DeliveryStatusRequest {
    public String token;
    public String version;
    @SerializedName("postman_id")
    public String uId;
    @SerializedName("delivery_id")
    public String deliveryId;
}
