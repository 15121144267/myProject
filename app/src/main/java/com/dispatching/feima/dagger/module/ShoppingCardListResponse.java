package com.dispatching.feima.dagger.module;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/8/9.
 */

public class ShoppingCardListResponse implements Serializable {

    /**
     * data : [{"count":5,"linkId":"53c69e54-c788-495c-bed3-2dbfc6fd5c61_001","products":[{"barcode":"6957506716178","category":"","categoryName":"","companyId":"53c69e54-c788-495c-bed3-2dbfc6fd5c61","customerCode":"2","finalPrice":1000,"labelNames":[],"name":"芳香消臭剂","originalPrice":1000,"picture":"http://ojcpa0rpt.bkt.clouddn.com/xianfengfruit_DishPool_default_53c69e54-c788-495c-bed3-2dbfc6fd5c61_1499930987.jpg","pid":"1461","productNumber":5,"remark":"","saleCount":"0","sellTimeName":"","sequence":0,"specification":"160/薰衣草味","status":"2","type":1,"unit":"克"}],"scid":83357909072740622,"type":1,"userId":"e18cc6af-60c7-4f31-b339-2d5426eca9df"},{"count":4,"linkId":"53c69e54-c788-495c-bed3-2dbfc6fd5c61_107","products":[{"barcode":"","category":"","categoryName":"鞋","companyId":"53c69e54-c788-495c-bed3-2dbfc6fd5c61","customerCode":"","finalPrice":268000,"labelNames":[],"name":"低帮灰色斑点底面","originalPrice":268000,"picture":"http://ojcpa0rpt.bkt.clouddn.com/xianfengfruit_DishPool_default_17ef7396-4c56-4e08-9f3f-5cba520234d6_1499772742.jpg","pid":"1273","productNumber":3,"remark":"低帮灰色斑点底面","saleCount":"0","sellTimeName":"a","sequence":0,"specification":"43","status":"2","type":1,"unit":"个"},{"barcode":"","category":"","categoryName":"项链","companyId":"53c69e54-c788-495c-bed3-2dbfc6fd5c61","customerCode":"","finalPrice":14800,"labelNames":[],"name":"十字架项链","originalPrice":14800,"picture":"http://ojcpa0rpt.bkt.clouddn.com/xianfengfruit_DishPool_default_17ef7396-4c56-4e08-9f3f-5cba520234d6_1499774669.jpg","pid":"1357","productNumber":1,"remark":"十字架项链","saleCount":"0","sellTimeName":"a","sequence":0,"specification":"银色","status":"2","type":1,"unit":"个"}],"scid":83351757455884549,"type":1,"userId":"e18cc6af-60c7-4f31-b339-2d5426eca9df"}]
     * errcode : 100
     * errmsg : 查询用户商户所有门店购物车商品
     */

    public List<DataBean> data;

    public static class DataBean {
        /**
         * count : 5
         * linkId : 53c69e54-c788-495c-bed3-2dbfc6fd5c61_001
         * products : [{"barcode":"6957506716178","category":"","categoryName":"","companyId":"53c69e54-c788-495c-bed3-2dbfc6fd5c61","customerCode":"2","finalPrice":1000,"labelNames":[],"name":"芳香消臭剂","originalPrice":1000,"picture":"http://ojcpa0rpt.bkt.clouddn.com/xianfengfruit_DishPool_default_53c69e54-c788-495c-bed3-2dbfc6fd5c61_1499930987.jpg","pid":"1461","productNumber":5,"remark":"","saleCount":"0","sellTimeName":"","sequence":0,"specification":"160/薰衣草味","status":"2","type":1,"unit":"克"}]
         * scid : 83357909072740622
         * type : 1
         * userId : e18cc6af-60c7-4f31-b339-2d5426eca9df
         */
        public boolean childEditFlag = false;
        public boolean checkFlag = false;
        public int count;
        public String linkId;
        public long scid;
        public int type;
        public String userId;
        public List<ProductsBean> products;

        public static class ProductsBean {
            /**
             * barcode : 6957506716178
             * category :
             * categoryName :
             * companyId : 53c69e54-c788-495c-bed3-2dbfc6fd5c61
             * customerCode : 2
             * finalPrice : 1000
             * labelNames : []
             * name : 芳香消臭剂
             * originalPrice : 1000
             * picture : http://ojcpa0rpt.bkt.clouddn.com/xianfengfruit_DishPool_default_53c69e54-c788-495c-bed3-2dbfc6fd5c61_1499930987.jpg
             * pid : 1461
             * productNumber : 5
             * remark :
             * saleCount : 0
             * sellTimeName :
             * sequence : 0
             * specification : 160/薰衣草味
             * status : 2
             * type : 1
             * unit : 克
             */
            public boolean childCheckFlag = false;
            public boolean childEditFlag = false;
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
            public String remark;
            public String saleCount;
            public String sellTimeName;
            public int sequence;
            public String specification;
            public String status;
            public int type;
            public String unit;
            public List<?> labelNames;
        }
    }
}
