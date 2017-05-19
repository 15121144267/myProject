package com.dispatching.feima.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by helei on 2017/5/2.
 * LoginRequest
 */

public class LoginRequest {

    public String phone;

    @SerializedName("verify_code")
    public String verifyCode;
}
