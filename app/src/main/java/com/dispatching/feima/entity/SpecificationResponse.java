package com.dispatching.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/7/14.
 */

public class SpecificationResponse implements Serializable {

    /**
     * count : 1
     * products : [{"barcode":"","category":"","categoryName":"生活用品","companyId":"53c69e54-c788-495c-bed3-2dbfc6fd5c61","customerCode":"","finalPrice":328000,"labelNames":[],"name":"白鞋黑高帮","originalPrice":328000,"picture":"http://ojcpa0rpt.bkt.clouddn.com/xianfengfruit_DishPool_default_5d3d1a22-c822-42fb-9688-8328803ea81c_1500008924.jpg","pid":"1415","productSpecification":[{"color":"黑高帮","size":"39","productId":1263},{"color":"黑高帮","size":"40","productId":1264},{"color":"黑高帮","size":"41","productId":1265},{"color":"白高帮","size":"40","productId":1266},{"color":"白高帮","size":"41","productId":1267},{"color":"白高帮","size":"42","productId":1268},{"color":"黑纹高帮","size":"39","productId":1279},{"color":"黑纹高帮","size":"40","productId":1280},{"color":"黑纹高帮","size":"41","productId":1281}],"remark":"","saleCount":0,"sellTimeName":"可售时间","specification":"码","specificationList":[{"partName":"color","value":["黑高帮","白高帮","黑纹高帮"]},{"partName":"size","value":["39","40","41","42"]}],"status":2,"stock":0,"type":6,"unit":"双"}]
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
         * finalPrice : 328000
         * labelNames : []
         * name : 白鞋黑高帮
         * originalPrice : 328000
         * picture : http://ojcpa0rpt.bkt.clouddn.com/xianfengfruit_DishPool_default_5d3d1a22-c822-42fb-9688-8328803ea81c_1500008924.jpg
         * pid : 1415
         * productSpecification : [{"color":"黑高帮","size":"39","productId":1263},{"color":"黑高帮","size":"40","productId":1264},{"color":"黑高帮","size":"41","productId":1265},{"color":"白高帮","size":"40","productId":1266},{"color":"白高帮","size":"41","productId":1267},{"color":"白高帮","size":"42","productId":1268},{"color":"黑纹高帮","size":"39","productId":1279},{"color":"黑纹高帮","size":"40","productId":1280},{"color":"黑纹高帮","size":"41","productId":1281}]
         * remark :
         * saleCount : 0
         * sellTimeName : 可售时间
         * specification : 码
         * specificationList : [{"partName":"color","value":["黑高帮","白高帮","黑纹高帮"]},{"partName":"size","value":["39","40","41","42"]}]
         * status : 2
         * stock : 0
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
        public String remark;
        public int saleCount;
        public String sellTimeName;
        public String specification;
        public int status;
        public int stock;
        public int type;
        public String unit;
        public List<?> labelNames;
        public List<ProductSpecificationBean> productSpecification;
        public List<SpecificationListBean> specificationList;

        public static class ProductSpecificationBean {
            /**
             * color : 黑高帮
             * size : 39
             * productId : 1263
             */

            public String color;
            public String size;
            public int productId;
            public String zipper;
        }

        public static class SpecificationListBean {
            /**
             * partName : color
             * value : ["黑高帮","白高帮","黑纹高帮"]
             */

            public String partName;
            public List<String> value;
        }
    }
}
