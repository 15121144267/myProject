package com.dispatching.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/8/28.
 */

public class ClassifySearchAllListResponse implements Serializable {

    /**
     * data : [{"children":[{"children":[],"nodeModel":{"hasChild":0,"level":3,"menuId":666,"name":"莫名其妙的菜品节点","nid":"01010101","productId":1415,"remark":"","sequence":10199,"status":2,"type":"菜品"},"resultModel":{"barcode":"","category":"","categoryName":"生活用品","companyId":"53c69e54-c788-495c-bed3-2dbfc6fd5c61","customerCode":"","finalPrice":1320000,"labelNames":[],"name":"白鞋高帮规格商品","originalPrice":1320000,"picture":"http://ojcpa0rpt.bkt.clouddn.com/xianfengfruit_DishPool_default_17ef7396-4c56-4e08-9f3f-5cba520234d6_1501580958.jpg","pid":"1415","productSpecification":[{"color":"黑高帮","size":"41","productId":1265},{"color":"黑高帮","size":"40","productId":1264},{"color":"黑纹高帮","size":"41","productId":1281},{"color":"黑纹高帮","size":"40","productId":1280},{"color":"黑纹高帮","size":"39","productId":1279},{"color":"黑高帮","size":"39","productId":1263},{"color":"白高帮","size":"41","productId":1267},{"color":"白高帮","size":"40","productId":1266},{"color":"白高帮","size":"42","productId":1268}],"remark":"白鞋高帮","saleCount":0,"sellTimeName":"可售时间","sequence":0,"specification":"码","specificationList":[{"partName":"color","value":["黑高帮","白高帮","黑纹高帮"]},{"partName":"size","value":["39","40","41","42"]}],"status":2,"stock":0,"stockLimit":1,"type":6,"unit":"双"}},{"children":[],"nodeModel":{"hasChild":0,"level":3,"menuId":666,"name":"莫名其妙的菜品节点","nid":"01010102","productId":1416,"remark":"","sequence":10199,"status":2,"type":"菜品"},"resultModel":{"barcode":"","category":"","categoryName":"生活用品","companyId":"53c69e54-c788-495c-bed3-2dbfc6fd5c61","customerCode":"","finalPrice":2050000,"labelNames":[],"name":"三角形高帮规格商品","originalPrice":2050000,"picture":"http://ojcpa0rpt.bkt.clouddn.com/xianfengfruit_DishPool_default_17ef7396-4c56-4e08-9f3f-5cba520234d6_1501581277.jpg","pid":"1416","productSpecification":[{"color":"黑色","size":"37","productId":1283},{"color":"白色","size":"39","productId":1275},{"color":"黑色","size":"38","productId":1284},{"color":"黑色","size":"39","productId":1285},{"color":"白色","size":"37","productId":1274},{"color":"黑色","size":"36","productId":1282},{"color":"黑色","size":"40","productId":1286},{"color":"白色","size":"42","productId":1278},{"color":"白色","size":"40","productId":1276},{"color":"白色","size":"41","productId":1277}],"remark":"三角形高帮","saleCount":0,"sellTimeName":"可售时间","sequence":0,"specification":"码","specificationList":[{"partName":"color","value":["黑色","白色"]},{"partName":"size","value":["36","37","38","39","40","41","42"]}],"status":2,"stock":0,"stockLimit":1,"type":6,"unit":"双"}}],"nodeModel":{"hasChild":1,"level":2,"menuId":666,"name":"鞋子标签","nid":"010101","productId":0,"remark":"","sequence":10199,"status":2,"type":"菜品"},"resultModel":{"hasChild":1,"level":2,"menuId":666,"name":"鞋子标签","nid":"010101","productId":0,"remark":"","sequence":10199,"status":2,"type":"菜品"}}]
     * errcode : 100
     * errmsg : 查询菜单下一级信息成功
     */

    public List<DataBean> data;

    public static class DataBean {
        /**
         * children : [{"children":[],"nodeModel":{"hasChild":0,"level":3,"menuId":666,"name":"莫名其妙的菜品节点","nid":"01010101","productId":1415,"remark":"","sequence":10199,"status":2,"type":"菜品"},"resultModel":{"barcode":"","category":"","categoryName":"生活用品","companyId":"53c69e54-c788-495c-bed3-2dbfc6fd5c61","customerCode":"","finalPrice":1320000,"labelNames":[],"name":"白鞋高帮规格商品","originalPrice":1320000,"picture":"http://ojcpa0rpt.bkt.clouddn.com/xianfengfruit_DishPool_default_17ef7396-4c56-4e08-9f3f-5cba520234d6_1501580958.jpg","pid":"1415","productSpecification":[{"color":"黑高帮","size":"41","productId":1265},{"color":"黑高帮","size":"40","productId":1264},{"color":"黑纹高帮","size":"41","productId":1281},{"color":"黑纹高帮","size":"40","productId":1280},{"color":"黑纹高帮","size":"39","productId":1279},{"color":"黑高帮","size":"39","productId":1263},{"color":"白高帮","size":"41","productId":1267},{"color":"白高帮","size":"40","productId":1266},{"color":"白高帮","size":"42","productId":1268}],"remark":"白鞋高帮","saleCount":0,"sellTimeName":"可售时间","sequence":0,"specification":"码","specificationList":[{"partName":"color","value":["黑高帮","白高帮","黑纹高帮"]},{"partName":"size","value":["39","40","41","42"]}],"status":2,"stock":0,"stockLimit":1,"type":6,"unit":"双"}},{"children":[],"nodeModel":{"hasChild":0,"level":3,"menuId":666,"name":"莫名其妙的菜品节点","nid":"01010102","productId":1416,"remark":"","sequence":10199,"status":2,"type":"菜品"},"resultModel":{"barcode":"","category":"","categoryName":"生活用品","companyId":"53c69e54-c788-495c-bed3-2dbfc6fd5c61","customerCode":"","finalPrice":2050000,"labelNames":[],"name":"三角形高帮规格商品","originalPrice":2050000,"picture":"http://ojcpa0rpt.bkt.clouddn.com/xianfengfruit_DishPool_default_17ef7396-4c56-4e08-9f3f-5cba520234d6_1501581277.jpg","pid":"1416","productSpecification":[{"color":"黑色","size":"37","productId":1283},{"color":"白色","size":"39","productId":1275},{"color":"黑色","size":"38","productId":1284},{"color":"黑色","size":"39","productId":1285},{"color":"白色","size":"37","productId":1274},{"color":"黑色","size":"36","productId":1282},{"color":"黑色","size":"40","productId":1286},{"color":"白色","size":"42","productId":1278},{"color":"白色","size":"40","productId":1276},{"color":"白色","size":"41","productId":1277}],"remark":"三角形高帮","saleCount":0,"sellTimeName":"可售时间","sequence":0,"specification":"码","specificationList":[{"partName":"color","value":["黑色","白色"]},{"partName":"size","value":["36","37","38","39","40","41","42"]}],"status":2,"stock":0,"stockLimit":1,"type":6,"unit":"双"}}]
         * nodeModel : {"hasChild":1,"level":2,"menuId":666,"name":"鞋子标签","nid":"010101","productId":0,"remark":"","sequence":10199,"status":2,"type":"菜品"}
         * resultModel : {"hasChild":1,"level":2,"menuId":666,"name":"鞋子标签","nid":"010101","productId":0,"remark":"","sequence":10199,"status":2,"type":"菜品"}
         */

        public NodeModelBean nodeModel;
        public ResultModelBean resultModel;
        public List<ChildrenBean> children;

        public static class NodeModelBean {
            /**
             * hasChild : 1
             * level : 2
             * menuId : 666
             * name : 鞋子标签
             * nid : 010101
             * productId : 0
             * remark :
             * sequence : 10199
             * status : 2
             * type : 菜品
             */

            public int hasChild;
            public int level;
            public int menuId;
            public String name;
            public String nid;
            public int productId;
            public String remark;
            public int sequence;
            public int status;
            public String type;
        }

        public static class ResultModelBean {
            /**
             * hasChild : 1
             * level : 2
             * menuId : 666
             * name : 鞋子标签
             * nid : 010101
             * productId : 0
             * remark :
             * sequence : 10199
             * status : 2
             * type : 菜品
             */

            public int hasChild;
            public int level;
            public int menuId;
            public String name;
            public String nid;
            public int productId;
            public String remark;
            public int sequence;
            public int status;
            public String type;
        }

        public static class ChildrenBean {
            /**
             * children : []
             * nodeModel : {"hasChild":0,"level":3,"menuId":666,"name":"莫名其妙的菜品节点","nid":"01010101","productId":1415,"remark":"","sequence":10199,"status":2,"type":"菜品"}
             * resultModel : {"barcode":"","category":"","categoryName":"生活用品","companyId":"53c69e54-c788-495c-bed3-2dbfc6fd5c61","customerCode":"","finalPrice":1320000,"labelNames":[],"name":"白鞋高帮规格商品","originalPrice":1320000,"picture":"http://ojcpa0rpt.bkt.clouddn.com/xianfengfruit_DishPool_default_17ef7396-4c56-4e08-9f3f-5cba520234d6_1501580958.jpg","pid":"1415","productSpecification":[{"color":"黑高帮","size":"41","productId":1265},{"color":"黑高帮","size":"40","productId":1264},{"color":"黑纹高帮","size":"41","productId":1281},{"color":"黑纹高帮","size":"40","productId":1280},{"color":"黑纹高帮","size":"39","productId":1279},{"color":"黑高帮","size":"39","productId":1263},{"color":"白高帮","size":"41","productId":1267},{"color":"白高帮","size":"40","productId":1266},{"color":"白高帮","size":"42","productId":1268}],"remark":"白鞋高帮","saleCount":0,"sellTimeName":"可售时间","sequence":0,"specification":"码","specificationList":[{"partName":"color","value":["黑高帮","白高帮","黑纹高帮"]},{"partName":"size","value":["39","40","41","42"]}],"status":2,"stock":0,"stockLimit":1,"type":6,"unit":"双"}
             */

            public NodeModelBeanX nodeModel;
            public ResultModelBeanX resultModel;
            public List<?> children;

            public static class NodeModelBeanX {
                /**
                 * hasChild : 0
                 * level : 3
                 * menuId : 666
                 * name : 莫名其妙的菜品节点
                 * nid : 01010101
                 * productId : 1415
                 * remark :
                 * sequence : 10199
                 * status : 2
                 * type : 菜品
                 */

                public int hasChild;
                public int level;
                public int menuId;
                public String name;
                public String nid;
                public int productId;
                public String remark;
                public int sequence;
                public int status;
                public String type;
            }

            public static class ResultModelBeanX {
                /**
                 * barcode :
                 * category :
                 * categoryName : 生活用品
                 * companyId : 53c69e54-c788-495c-bed3-2dbfc6fd5c61
                 * customerCode :
                 * finalPrice : 1320000
                 * labelNames : []
                 * name : 白鞋高帮规格商品
                 * originalPrice : 1320000
                 * picture : http://ojcpa0rpt.bkt.clouddn.com/xianfengfruit_DishPool_default_17ef7396-4c56-4e08-9f3f-5cba520234d6_1501580958.jpg
                 * pid : 1415
                 * productSpecification : [{"color":"黑高帮","size":"41","productId":1265},{"color":"黑高帮","size":"40","productId":1264},{"color":"黑纹高帮","size":"41","productId":1281},{"color":"黑纹高帮","size":"40","productId":1280},{"color":"黑纹高帮","size":"39","productId":1279},{"color":"黑高帮","size":"39","productId":1263},{"color":"白高帮","size":"41","productId":1267},{"color":"白高帮","size":"40","productId":1266},{"color":"白高帮","size":"42","productId":1268}]
                 * remark : 白鞋高帮
                 * saleCount : 0
                 * sellTimeName : 可售时间
                 * sequence : 0
                 * specification : 码
                 * specificationList : [{"partName":"color","value":["黑高帮","白高帮","黑纹高帮"]},{"partName":"size","value":["39","40","41","42"]}]
                 * status : 2
                 * stock : 0
                 * stockLimit : 1
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
                public int sequence;
                public String specification;
                public int status;
                public int stock;
                public int stockLimit;
                public int type;
                public String unit;
                public List<?> labelNames;
                public List<ProductSpecificationBean> productSpecification;
                public List<SpecificationListBean> specificationList;

                public static class ProductSpecificationBean {
                    /**
                     * color : 黑高帮
                     * size : 41
                     * productId : 1265
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
    }
}
