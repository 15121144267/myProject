package com.dispatching.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/6/28.
 */

public class MyOrdersResponse implements Serializable {

    /**
     * count : 3
     * orders : [{"accounts":[{"accountId":"account-1","name":"运费","number":1,"orderId":80915232962117922,"price":500,"sequence":0,"type":1}],"address":"南二环与宿松路交口绿地中心半生缘街区","amount":20400,"cancelReason":"","companyId":"53c69e54-c788-495c-bed3-2dbfc6fd5c61","customerOrder":"BSY_1499931066406","evaluateStatus":1,"gmtCreate":1499931067000,"latitude":121.377685,"longitude":121.377685,"oid":80915232962117922,"partition":"","payChannel":"","payChannelName":"","payStatus":1,"payType":1,"phone":"","products":[{"barcode":"","category":"","categoryName":"帽子","companyId":"53c69e54-c788-495c-bed3-2dbfc6fd5c61","customerCode":"","finalPrice":19900,"labelNames":[],"name":"blackhead王冠帽子","originalPrice":19900,"picture":"http://ojcpa0rpt.bkt.clouddn.com/xianfengfruit_DishPool_default_17ef7396-4c56-4e08-9f3f-5cba520234d6_1499773655.jpg","pid":"1322","productNumber":1,"saleCount":0,"sellTimeName":"a","specification":"黑色","status":"2","type":1,"unit":"个"}],"remark":"","shopId":"53c69e54-c788-495c-bed3-2dbfc6fd5c61_107","shopName":"LET  ME  SEE","source":"","status":2,"type":1,"userId":"004f55aa-eb49-4b0a-b5ed-ca42b21ac5c2","userName":"15121144267"},{"accounts":[{"accountId":"account-1","name":"运费","number":1,"orderId":80917374650286378,"price":500,"sequence":0,"type":1}],"address":"南二环与宿松路交口绿地中心半生缘街区","amount":20400,"cancelReason":"","companyId":"53c69e54-c788-495c-bed3-2dbfc6fd5c61","customerOrder":"BSY_1499933111058","evaluateStatus":1,"gmtCreate":1499933110000,"latitude":121.377686,"longitude":121.377686,"oid":80917374650286378,"partition":"","payChannel":"","payChannelName":"","payStatus":1,"payType":1,"phone":"","products":[{"barcode":"","category":"","categoryName":"帽子","companyId":"53c69e54-c788-495c-bed3-2dbfc6fd5c61","customerCode":"","finalPrice":19900,"labelNames":[],"name":"blackhead王冠帽子","originalPrice":19900,"picture":"http://ojcpa0rpt.bkt.clouddn.com/xianfengfruit_DishPool_default_17ef7396-4c56-4e08-9f3f-5cba520234d6_1499773655.jpg","pid":"1322","productNumber":1,"saleCount":0,"sellTimeName":"a","specification":"黑色","status":"2","type":1,"unit":"个"}],"remark":"","shopId":"53c69e54-c788-495c-bed3-2dbfc6fd5c61_107","shopName":"LET  ME  SEE","source":"","status":2,"type":1,"userId":"004f55aa-eb49-4b0a-b5ed-ca42b21ac5c2","userName":"15121144267"},{"accounts":[{"accountId":"account-1","name":"运费","number":1,"orderId":80918480647356721,"price":500,"sequence":0,"type":1}],"address":"南二环与宿松路交口绿地中心半生缘街区","amount":20400,"cancelReason":"","companyId":"53c69e54-c788-495c-bed3-2dbfc6fd5c61","customerOrder":"BSY_1499934161325","evaluateStatus":1,"gmtCreate":1499934164000,"latitude":121.377673,"longitude":121.377673,"oid":80918480647356721,"partition":"","payChannel":"","payChannelName":"","payStatus":1,"payType":1,"phone":"","products":[{"barcode":"","category":"","categoryName":"帽子","companyId":"53c69e54-c788-495c-bed3-2dbfc6fd5c61","customerCode":"","finalPrice":19900,"labelNames":[],"name":"blackhead王冠帽子","originalPrice":19900,"picture":"http://ojcpa0rpt.bkt.clouddn.com/xianfengfruit_DishPool_default_17ef7396-4c56-4e08-9f3f-5cba520234d6_1499773655.jpg","pid":"1322","productNumber":1,"saleCount":0,"sellTimeName":"a","specification":"黑色","status":"2","type":1,"unit":"个"}],"remark":"","shopId":"53c69e54-c788-495c-bed3-2dbfc6fd5c61_107","shopName":"LET  ME  SEE","source":"","status":2,"type":1,"userId":"004f55aa-eb49-4b0a-b5ed-ca42b21ac5c2","userName":"15121144267"}]
     */

    public int count;
    public List<OrdersBean> orders;

    public static class OrdersBean implements Serializable{
        /**
         * accounts : [{"accountId":"account-1","name":"运费","number":1,"orderId":80915232962117922,"price":500,"sequence":0,"type":1}]
         * address : 南二环与宿松路交口绿地中心半生缘街区
         * amount : 20400
         * cancelReason :
         * companyId : 53c69e54-c788-495c-bed3-2dbfc6fd5c61
         * customerOrder : BSY_1499931066406
         * evaluateStatus : 1
         * gmtCreate : 1499931067000
         * latitude : 121.377685
         * longitude : 121.377685
         * oid : 80915232962117922
         * partition :
         * payChannel :
         * payChannelName :
         * payStatus : 1
         * payType : 1
         * phone :
         * products : [{"barcode":"","category":"","categoryName":"帽子","companyId":"53c69e54-c788-495c-bed3-2dbfc6fd5c61","customerCode":"","finalPrice":19900,"labelNames":[],"name":"blackhead王冠帽子","originalPrice":19900,"picture":"http://ojcpa0rpt.bkt.clouddn.com/xianfengfruit_DishPool_default_17ef7396-4c56-4e08-9f3f-5cba520234d6_1499773655.jpg","pid":"1322","productNumber":1,"saleCount":0,"sellTimeName":"a","specification":"黑色","status":"2","type":1,"unit":"个"}]
         * remark :
         * shopId : 53c69e54-c788-495c-bed3-2dbfc6fd5c61_107
         * shopName : LET  ME  SEE
         * source :
         * status : 2
         * type : 1
         * userId : 004f55aa-eb49-4b0a-b5ed-ca42b21ac5c2
         * userName : 15121144267
         */

        public String address;
        public int amount;
        public String cancelReason;
        public String companyId;
        public String customerOrder;
        public int evaluateStatus;
        public long gmtCreate;
        public double latitude;
        public double longitude;
        public long oid;
        public String partition;
        public String payChannel;
        public String payChannelName;
        public int payStatus;
        public int payType;
        public String phone;
        public String remark;
        public String shopId;
        public String shopName;
        public String source;
        public int status;
        public int type;
        public String userId;
        public String userName;
        public List<AccountsBean> accounts;
        public List<ProductsBean> products;

        public static class AccountsBean implements Serializable{
            /**
             * accountId : account-1
             * name : 运费
             * number : 1
             * orderId : 80915232962117922
             * price : 500
             * sequence : 0
             * type : 1
             */

            public String accountId;
            public String name;
            public int number;
            public long orderId;
            public int price;
            public int sequence;
            public int type;
        }

        public static class ProductsBean implements Serializable{
            /**
             * barcode :
             * category :
             * categoryName : 帽子
             * companyId : 53c69e54-c788-495c-bed3-2dbfc6fd5c61
             * customerCode :
             * finalPrice : 19900
             * labelNames : []
             * name : blackhead王冠帽子
             * originalPrice : 19900
             * picture : http://ojcpa0rpt.bkt.clouddn.com/xianfengfruit_DishPool_default_17ef7396-4c56-4e08-9f3f-5cba520234d6_1499773655.jpg
             * pid : 1322
             * productNumber : 1
             * saleCount : 0
             * sellTimeName : a
             * specification : 黑色
             * status : 2
             * type : 1
             * unit : 个
             */

            public String barcode;
            public String category;
            public String categoryName;
            public String companyId;
            public String customerCode;
            public int finalPrice;
            public String name;
            public int originalPrice;
            public String picture;
            public String pid;
            public int productNumber;
            public int saleCount;
            public String sellTimeName;
            public String specification;
            public String status;
            public int type;
            public String unit;
            public List<?> labelNames;
        }
    }

}
