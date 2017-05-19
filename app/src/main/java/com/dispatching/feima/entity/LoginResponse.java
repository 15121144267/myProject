package com.dispatching.feima.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by helei on 2017/5/2.
 * LoginResponse
 */

public class LoginResponse implements Serializable {
    @SerializedName("postman_id")
    public String uId;

    @SerializedName("postman_name")
    public String uName;

    @SerializedName("store_name")
    public String storeName;

    public String token;
}
