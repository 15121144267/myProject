package com.dispatching.feima.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by helei on 2017/5/2.
 */

public class LoginResponse implements Serializable {
    @SerializedName("u_id")
    public String uId;

    @SerializedName("u_name")
    public String uName;
    @SerializedName("store_name")
    public String storeName;

    public String token;
}
