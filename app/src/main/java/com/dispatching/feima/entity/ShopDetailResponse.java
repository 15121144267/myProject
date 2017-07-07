package com.dispatching.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/6/30.
 */

public class ShopDetailResponse implements Serializable {
    public String productName;
    public String productPrice;
    public String productCount;


    /**
     * count : 63
     * products : [{"barcode":"","category":"","categoryName":"进口水果","companyId":"178a14ba-85a8-40c7-9ff4-6418418f5a0c","customerCode":"0622000250","finalPrice":1490,"labelNames":[{"lid":2,"name":"即时送","shopId":"178a14ba-85a8-40c7-9ff4-6418418f5a0c_31310040","type":1},{"lid":4,"name":"凑单","shopId":"178a14ba-85a8-40c7-9ff4-6418418f5a0c_31310040","type":5},{"lid":21,"name":"label3","shopId":"178a14ba-85a8-40c7-9ff4-6418418f5a0c_31310040","type":4},{"lid":24,"name":"label6","shopId":"178a14ba-85a8-40c7-9ff4-6418418f5a0c_31310040","type":4}],"name":"智利红提","originalPrice":1390,"picture":"http://oryfeje0z.bkt.clouddn.com/%E6%99%BA%E5%88%A9%E7%BA%A2%E6%8F%90120x180.jpg","pid":"1158","saleCount":0,"sellTimeName":"a","specification":"250g","status":1,"type":1,"unit":"g"},{"barcode":"","category":"","categoryName":"进口水果","companyId":"178a14ba-85a8-40c7-9ff4-6418418f5a0c","customerCode":"6527000500","finalPrice":7900,"labelNames":[{"lid":4,"name":"凑单","shopId":"178a14ba-85a8-40c7-9ff4-6418418f5a0c_31310040","type":5},{"lid":22,"name":"label4","shopId":"178a14ba-85a8-40c7-9ff4-6418418f5a0c_31310040","type":4}],"name":"美国车厘子","originalPrice":7800,"picture":"http://oryfeje0z.bkt.clouddn.com/%E8%BD%A6%E5%8E%98%E5%AD%90120x180.jpg","pid":"1159","saleCount":0,"sellTimeName":"a","specification":"500g","status":1,"type":1,"unit":"g"},{"barcode":"","category":"","categoryName":"进口水果","companyId":"178a14ba-85a8-40c7-9ff4-6418418f5a0c","customerCode":"0689000500","finalPrice":6900,"labelNames":[{"lid":2,"name":"即时送","shopId":"178a14ba-85a8-40c7-9ff4-6418418f5a0c_31310040","type":1},{"lid":4,"name":"凑单","shopId":"178a14ba-85a8-40c7-9ff4-6418418f5a0c_31310040","type":5}],"name":"黑加仑","originalPrice":6800,"picture":"http://oryfeje0z.bkt.clouddn.com/%E6%81%90%E9%BE%99%E8%9B%8B120x180.jpg","pid":"1162","saleCount":0,"sellTimeName":"a","specification":"500g","status":1,"type":1,"unit":"g"},{"barcode":"","category":"","categoryName":"进口水果","companyId":"178a14ba-85a8-40c7-9ff4-6418418f5a0c","customerCode":"0657000500","finalPrice":790,"labelNames":[{"lid":2,"name":"即时送","shopId":"178a14ba-85a8-40c7-9ff4-6418418f5a0c_31310040","type":1},{"lid":4,"name":"凑单","shopId":"178a14ba-85a8-40c7-9ff4-6418418f5a0c_31310040","type":5}],"name":"埃及橙","originalPrice":780,"picture":"http://oryfeje0z.bkt.clouddn.com/%E5%9F%83%E5%8F%8A%E6%A9%99120x180.jpg","pid":"1163","saleCount":0,"sellTimeName":"a","specification":"500g","status":1,"type":1,"unit":"g"},{"barcode":"","category":"","categoryName":"进口水果","companyId":"178a14ba-85a8-40c7-9ff4-6418418f5a0c","customerCode":"0654000500","finalPrice":990,"labelNames":[{"lid":2,"name":"即时送","shopId":"178a14ba-85a8-40c7-9ff4-6418418f5a0c_31310040","type":1}],"name":"美国橙","originalPrice":980,"picture":"http://oryfeje0z.bkt.clouddn.com/%E7%BE%8E%E5%9B%BD%E6%A9%99120x180.jpg","pid":"1164","saleCount":0,"sellTimeName":"a","specification":"500g","status":1,"type":1,"unit":"g"},{"barcode":"","category":"","categoryName":"进口水果","companyId":"178a14ba-85a8-40c7-9ff4-6418418f5a0c","customerCode":"0644000500","finalPrice":1690,"labelNames":[{"lid":2,"name":"即时送","shopId":"178a14ba-85a8-40c7-9ff4-6418418f5a0c_31310040","type":1}],"name":"南非柑","originalPrice":1680,"picture":"","pid":"1165","saleCount":0,"sellTimeName":"a","specification":"500g","status":1,"type":1,"unit":"g"},{"barcode":"","category":"","categoryName":"进口水果","companyId":"178a14ba-85a8-40c7-9ff4-6418418f5a0c","customerCode":"0723000500","finalPrice":1850,"labelNames":[{"lid":2,"name":"即时送","shopId":"178a14ba-85a8-40c7-9ff4-6418418f5a0c_31310040","type":1},{"lid":3,"name":"特价","shopId":"178a14ba-85a8-40c7-9ff4-6418418f5a0c_31310040","type":2}],"name":"特价山竹2A","originalPrice":1830,"picture":"","pid":"1167","saleCount":0,"sellTimeName":"a","specification":"500g","status":1,"type":1,"unit":"g"},{"barcode":"","category":"","categoryName":"进口水果","companyId":"178a14ba-85a8-40c7-9ff4-6418418f5a0c","customerCode":"0795000500","finalPrice":1390,"labelNames":[{"lid":2,"name":"即时送","shopId":"178a14ba-85a8-40c7-9ff4-6418418f5a0c_31310040","type":1}],"name":"干尧榴莲","originalPrice":1390,"picture":"http://oryfeje0z.bkt.clouddn.com/%E5%B9%B2%E5%B0%A7%E6%A6%B4%E8%8E%B2120x180.jpg","pid":"1168","saleCount":0,"sellTimeName":"a","specification":"500g","status":1,"type":1,"unit":"g"},{"barcode":"","category":"","categoryName":"进口水果","companyId":"178a14ba-85a8-40c7-9ff4-6418418f5a0c","customerCode":"1242000500","finalPrice":1390,"labelNames":[{"lid":2,"name":"即时送","shopId":"178a14ba-85a8-40c7-9ff4-6418418f5a0c_31310040","type":1}],"name":"18度红心火龙果","originalPrice":1390,"picture":"http://oryfeje0z.bkt.clouddn.com/18%E5%BA%A6%E7%BA%A2%E5%BF%83%E7%81%AB%E9%BE%99%E6%9E%9C120x180.jpg","pid":"1170","saleCount":0,"sellTimeName":"a","specification":"500g","status":1,"type":1,"unit":"g"},{"barcode":"","category":"","categoryName":"进口水果","companyId":"178a14ba-85a8-40c7-9ff4-6418418f5a0c","customerCode":"0731000500","finalPrice":1390,"labelNames":[{"lid":2,"name":"即时送","shopId":"178a14ba-85a8-40c7-9ff4-6418418f5a0c_31310040","type":1}],"name":"金火龙果","originalPrice":1390,"picture":"http://oryfeje0z.bkt.clouddn.com/%E9%87%91%E7%81%AB%E9%BE%99%E6%9E%9C120x180.jpg","pid":"1171","saleCount":0,"sellTimeName":"a","specification":"500g","status":1,"type":1,"unit":"g"}]
     */

    public int count;
    public List<ProductsBean> products;

    public static class ProductsBean {
        /**
         * barcode :
         * category :
         * categoryName : 进口水果
         * companyId : 178a14ba-85a8-40c7-9ff4-6418418f5a0c
         * customerCode : 0622000250
         * finalPrice : 1490
         * labelNames : [{"lid":2,"name":"即时送","shopId":"178a14ba-85a8-40c7-9ff4-6418418f5a0c_31310040","type":1},{"lid":4,"name":"凑单","shopId":"178a14ba-85a8-40c7-9ff4-6418418f5a0c_31310040","type":5},{"lid":21,"name":"label3","shopId":"178a14ba-85a8-40c7-9ff4-6418418f5a0c_31310040","type":4},{"lid":24,"name":"label6","shopId":"178a14ba-85a8-40c7-9ff4-6418418f5a0c_31310040","type":4}]
         * name : 智利红提
         * originalPrice : 1390
         * picture : http://oryfeje0z.bkt.clouddn.com/%E6%99%BA%E5%88%A9%E7%BA%A2%E6%8F%90120x180.jpg
         * pid : 1158
         * saleCount : 0
         * sellTimeName : a
         * specification : 250g
         * status : 1
         * type : 1
         * unit : g
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
        public int saleCount;
        public String sellTimeName;
        public String specification;
        public int status;
        public int type;
        public String unit;
        public List<LabelNamesBean> labelNames;

        public static class LabelNamesBean {
            /**
             * lid : 2
             * name : 即时送
             * shopId : 178a14ba-85a8-40c7-9ff4-6418418f5a0c_31310040
             * type : 1
             */

            public int lid;
            public String name;
            public String shopId;
            public int type;
        }
    }
}
