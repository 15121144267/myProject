package com.dispatching.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/8/22.
 */

public class PayCreateRequest implements Serializable {
    public List<OrderConfirmedRequest> orders;
}
