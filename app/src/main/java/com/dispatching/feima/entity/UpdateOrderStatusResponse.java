package com.dispatching.feima.entity;

import java.io.Serializable;

/**
 * Created by lei.he on 2017/7/28.
 */

public class UpdateOrderStatusResponse implements Serializable {

    /**
     * alipayAmount : 1
     * fmId : SHT1A1553O1338427403
     * mcouponAmount : 0
     * operatorId : 2222
     * payDate : 2017-08-04 14:23:08.683
     * payEbcode : 10001
     * payId : 支付宝当面付[条码]
     * payTransId : 2017080421001004360267420678
     * refundAmount : 0
     * stationId : 2222
     * statusCode : 100
     * storeId : 107
     * totalAmount : 1
     * ver : 2
     */

    public int alipayAmount;
    public String fmId;
    public int mcouponAmount;
    public String operatorId;
    public String payDate;
    public String payEbcode;
    public String payId;
    public String payTransId;
    public int refundAmount;
    public String stationId;
    public int statusCode;
    public String storeId;
    public int totalAmount;
    public int ver;
}
