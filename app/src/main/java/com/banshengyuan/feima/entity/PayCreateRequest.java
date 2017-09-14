package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/8/22.
 * PayCreateRequest
 */

public class PayCreateRequest implements Serializable {
    public List<OrderConfirmedRequest> orders;
}
