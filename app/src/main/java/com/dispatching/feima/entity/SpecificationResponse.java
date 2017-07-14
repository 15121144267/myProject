package com.dispatching.feima.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/7/14.
 */

public class SpecificationResponse implements Serializable {


    /**
     * count : 1
     * products : [{"barcode":"","category":"","categoryName":"生活用品","companyId":"53c69e54-c788-495c-bed3-2dbfc6fd5c61","customerCode":"","finalPrice":0,"labelNames":[],"name":"低帮无拉链规格商品","originalPrice":0,"picture":"","pid":"1418","productSpecification":[{"颜色":"蓝色","productId":1296,"尺码":"37"},{"颜色":"蓝色","productId":1297,"尺码":"38"},{"颜色":"粉色","productId":1305,"尺码":"38"}],"saleCount":0,"sellTimeName":"可售时间","specification":"码/颜色","specificationList":{"颜色":["蓝色","粉色"],"尺码":["37","38"]},"status":2,"type":6,"unit":"双"}]
     */

    public int count;
    public List<ProductsBean> products;

    public static class ProductsBean {
        /**
         * barcode :
         * category :
         * categoryName : 生活用品
         * companyId : 53c69e54-c788-495c-bed3-2dbfc6fd5c61
         * customerCode :
         * finalPrice : 0
         * labelNames : []
         * name : 低帮无拉链规格商品
         * originalPrice : 0
         * picture :
         * pid : 1418
         * productSpecification : [{"颜色":"蓝色","productId":1296,"尺码":"37"},{"颜色":"蓝色","productId":1297,"尺码":"38"},{"颜色":"粉色","productId":1305,"尺码":"38"}]
         * saleCount : 0
         * sellTimeName : 可售时间
         * specification : 码/颜色
         * specificationList : {"颜色":["蓝色","粉色"],"尺码":["37","38"]}
         * status : 2
         * type : 6
         * unit : 双
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
        public SpecificationListBean specificationList;
        public int status;
        public int type;
        public String unit;
        public List<?> labelNames;
        public List<ProductSpecificationBean> productSpecification;

        public static class SpecificationListBean {
            @SerializedName("颜色")
            public List<String> colorList;
            @SerializedName("尺码")
            public List<String> sizeList;
        }

        public static class ProductSpecificationBean {
            /**
             * 颜色 : 蓝色
             * productId : 1296
             * 尺码 : 37
             */
            @SerializedName("颜色")
            public String color;

            @SerializedName("尺码")
            public String size;

            public int productId;
        }
    }
}
