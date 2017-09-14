package com.banshengyuan.feima.entity;

import java.util.List;

/**
 * Created by lei.he on 2017/7/25.
 * PayRequest
 */

public class PayRequest {

    public List<OrdersBean> orders;

    public static class OrdersBean {
        /**
         * orderId : 84527112050966785
         * pay_ebcode : 10001
         */

        public String orderId;
        public String pay_ebcode;
    }
}
