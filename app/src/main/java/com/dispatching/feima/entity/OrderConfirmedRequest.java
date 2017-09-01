package com.dispatching.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/7/13.
 * OrderConfirmedRequest
 */

public class OrderConfirmedRequest implements Serializable{

    /**
     * shopName : 大创生活馆
     * payChannelName :
     * source : ios app
     * address : 地址
     * customerOrder : BSY_149991505404330
     * userName : 15600703631
     * products : [{"productName":"芳香消臭剂","number":"1","sequence":"0","specification":"茶色 XL","productId":"1404","price":0}]
     * amount : 1490
     * type : 1
     * payType : 1
     * userId : 3a115bc5-4243-4b47-879c-8f7aca71c584
     * payChannel :
     * latitude : 31.267283
     * status : 1
     * shopId : 178a14ba-85a8-40c7-9ff4-6418418f5a0c_31310040
     * phone : 15600703631
     * remark :
     * longitude : 121.377436
     * partition :
     * companyId : 53c69e54-c788-495c-bed3-2dbfc6fd5c61
     * accounts : [{"sequence":0,"accountId":"account-1","number":"1","name":"运费","type":"1","price":"500"}]
     */

    public String shopName;
    public String payChannelName;
    public String source;
    public String address;
    public String customerOrder;
    public String userName;
    public int amount;
    public int type;
    public int payType;
    public String userId;
    public String payChannel;
    public String latitude;
    public int status;
    public String shopId;
    public String phone;
    public String remark;
    public String longitude;
    public String partition;
    public String companyId;
    public List<ProductsBean> products;
    public List<AccountsBean> accounts;

    public static class ProductsBean implements Serializable{
        /**
         * productName : 芳香消臭剂
         * number : 1
         * sequence : 0
         * specification : 茶色 XL
         * productId : 1404
         * price : 0
         */

        public String productName;
        public String number;
        public String sequence;
        public String specification;
        public String productId;
        public String picture;
        public int price;
    }

    public static class AccountsBean implements Serializable{
        /**
         * sequence : 0
         * accountId : account-1
         * number : 1
         * name : 运费
         * type : 1
         * price : 500
         */

        public int sequence;
        public String accountId;
        public String number;
        public String name;
        public String type;
        public String price;
    }
}
