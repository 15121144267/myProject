package com.dispatching.feima.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by helei on 2017/5/3.
 */

public class OrderDeliveryRequest {
    public String token;
    public String version;
    @SerializedName("postman_id")
    public String uId;
    @SerializedName("start_time")
    public String startTime;
    @SerializedName("end_time")
    public String endTime;


}
