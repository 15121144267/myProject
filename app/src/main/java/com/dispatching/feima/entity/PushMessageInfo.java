package com.dispatching.feima.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by lei.he on 2017/6/14.
 */

public class PushMessageInfo implements Serializable {
    @SerializedName("business_id")
    public String businessId;

    @SerializedName("distribute_time")
    public String distributeTime;

    public String channel;
}
