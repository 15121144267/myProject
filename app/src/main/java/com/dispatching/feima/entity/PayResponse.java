package com.dispatching.feima.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by lei.he on 2017/7/25.
 */

public class PayResponse implements Serializable {

    /**
     * actInfo :
     * biz_content : app_id=2017071207729556&biz_content=%7b%22out_trade_no%22%3a%22SHT1A1553O1338398903%22%2c%22seller_id%22%3a%22%22%2c%22total_amount%22%3a%220.01%22%2c%22subject%22%3a%22%e5%8d%8a%e7%94%9f%e7%bc%98%22%2c%22goods_detail%22%3a%5b%7b%22goods_id%22%3a%221280%22%2c%22goods_name%22%3a%22%e6%9c%aa%e7%9f%a5%e5%95%86%e5%93%81%22%2c%22quantity%22%3a%221%22%2c%22price%22%3a%223280%22%7d%5d%2c%22store_id%22%3a%22107%22%7d&charset=utf-8&method=alipay.trade.app.pay&notify_url=http%3a%2f%2f115.159.142.32%2fapi%2falipaynotify%2f1553&prod_code=QUICK_MSECURITY_PAY&sign_type=RSA&timestamp=2017-07-25+11%3a06%3a02&version=1.0&sign=Nx3M3%2be0HICLEQRdo6CE%2b8FoCM%2bcfUkCSMMMbzLRDjYmW8CduGBNuliRaD%2bWdS6AKv7WxGZ8zs4ZsiwciNYKUkEednN1meEKvVuA5%2f1PnHvpEJBPqkaRCS5Vuq7g%2bx7pU33Xby31%2bfu442P%2bL%2fzcPcDNz5XGBY7epn48dNK%2f5t0%3d
     * fmId : SHT1A1553O1338398903
     * msg : OK
     * pay_acount : 支付宝账号:
     * pay_ebcode : 10001
     * pay_id : 支付宝在线支付
     * pay_order : {"appid":"","mch_id":"","nonce_str":"","package":"Sign=WXPay","prepay_id":"","sign":"","timestamp":"","trade_type":""}
     * pay_transId : 81966152753873174
     * statusCode : 100
     */

    public String actInfo;
    public String biz_content;
    public String fmId;
    public String msg;
    public String pay_acount;
    public int pay_ebcode;
    public String pay_id;
    public PayOrderBean pay_order;
    public String pay_transId;
    public int statusCode;

    public static class PayOrderBean {
        /**
         * appid :
         * mch_id :
         * nonce_str :
         * package : Sign=WXPay
         * prepay_id :
         * sign :
         * timestamp :
         * trade_type :
         */

        public String appid;
        public String mch_id;
        public String nonce_str;
        @SerializedName("package")
        public String packageX;
        public String prepay_id;
        public String sign;
        public String timestamp;
        public String trade_type;
    }
}
