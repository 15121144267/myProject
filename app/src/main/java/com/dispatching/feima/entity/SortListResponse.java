package com.dispatching.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/8/25.
 * SortListResponse
 */

public class SortListResponse implements Serializable {

    /**
     * data : [{"children":[{"children":[],"nodeModel":{"hasChild":1,"level":2,"menuId":666,"name":"鞋子标签","nid":"010101","productId":0,"remark":"","sequence":10199,"status":2,"type":"菜品"},"resultModel":{"hasChild":1,"level":2,"menuId":666,"name":"鞋子标签","nid":"010101","productId":0,"remark":"","sequence":10199,"status":2,"type":"菜品"}}],"nodeModel":{"hasChild":1,"level":1,"menuId":666,"name":"鞋","nid":"0101","productId":0,"remark":"","sequence":199,"status":2,"type":"一级分类"},"resultModel":{"hasChild":1,"level":1,"menuId":666,"name":"鞋","nid":"0101","productId":0,"remark":"","sequence":199,"status":2,"type":"一级分类"}},{"children":[{"children":[],"nodeModel":{"hasChild":1,"level":2,"menuId":666,"name":"包的标签1","nid":"010201","productId":0,"remark":"","sequence":10299,"status":2,"type":"菜品"},"resultModel":{"hasChild":1,"level":2,"menuId":666,"name":"包的标签1","nid":"010201","productId":0,"remark":"","sequence":10299,"status":2,"type":"菜品"}},{"children":[],"nodeModel":{"hasChild":1,"level":2,"menuId":666,"name":"包的标签2","nid":"010202","productId":0,"remark":"","sequence":10299,"status":2,"type":"菜品"},"resultModel":{"hasChild":1,"level":2,"menuId":666,"name":"包的标签2","nid":"010202","productId":0,"remark":"","sequence":10299,"status":2,"type":"菜品"}}],"nodeModel":{"hasChild":1,"level":1,"menuId":666,"name":"包","nid":"0102","productId":0,"remark":"","sequence":199,"status":2,"type":"一级分类"},"resultModel":{"hasChild":1,"level":1,"menuId":666,"name":"包","nid":"0102","productId":0,"remark":"","sequence":199,"status":2,"type":"一级分类"}},{"children":[{"children":[],"nodeModel":{"hasChild":1,"level":2,"menuId":666,"name":"帽子标签1","nid":"010301","productId":0,"remark":"","sequence":10399,"status":2,"type":"菜品"},"resultModel":{"hasChild":1,"level":2,"menuId":666,"name":"帽子标签1","nid":"010301","productId":0,"remark":"","sequence":10399,"status":2,"type":"菜品"}}],"nodeModel":{"hasChild":1,"level":1,"menuId":666,"name":"帽子","nid":"0103","productId":0,"remark":"","sequence":199,"status":2,"type":"一级分类"},"resultModel":{"hasChild":1,"level":1,"menuId":666,"name":"帽子","nid":"0103","productId":0,"remark":"","sequence":199,"status":2,"type":"一级分类"}},{"children":[{"children":[],"nodeModel":{"hasChild":1,"level":2,"menuId":666,"name":"配饰的标签","nid":"010401","productId":0,"remark":"","sequence":10499,"status":2,"type":"菜品"},"resultModel":{"hasChild":1,"level":2,"menuId":666,"name":"配饰的标签","nid":"010401","productId":0,"remark":"","sequence":10499,"status":2,"type":"菜品"}}],"nodeModel":{"hasChild":1,"level":1,"menuId":666,"name":"配饰","nid":"0104","productId":0,"remark":"","sequence":199,"status":2,"type":"一级分类"},"resultModel":{"hasChild":1,"level":1,"menuId":666,"name":"配饰","nid":"0104","productId":0,"remark":"","sequence":199,"status":2,"type":"一级分类"}},{"children":[{"children":[],"nodeModel":{"hasChild":1,"level":2,"menuId":666,"name":"外套标签1","nid":"010501","productId":0,"remark":"","sequence":10599,"status":2,"type":"菜品"},"resultModel":{"hasChild":1,"level":2,"menuId":666,"name":"外套标签1","nid":"010501","productId":0,"remark":"","sequence":10599,"status":2,"type":"菜品"}},{"children":[],"nodeModel":{"hasChild":1,"level":2,"menuId":666,"name":"外套标签2","nid":"010502","productId":0,"remark":"","sequence":10599,"status":2,"type":"菜品"},"resultModel":{"hasChild":1,"level":2,"menuId":666,"name":"外套标签2","nid":"010502","productId":0,"remark":"","sequence":10599,"status":2,"type":"菜品"}}],"nodeModel":{"hasChild":1,"level":1,"menuId":666,"name":"外套","nid":"0105","productId":0,"remark":"","sequence":199,"status":2,"type":"一级分类"},"resultModel":{"hasChild":1,"level":1,"menuId":666,"name":"外套","nid":"0105","productId":0,"remark":"","sequence":199,"status":2,"type":"一级分类"}},{"children":[{"children":[],"nodeModel":{"hasChild":1,"level":2,"menuId":666,"name":"裤子标签1","nid":"010601","productId":0,"remark":"","sequence":10699,"status":2,"type":"菜品"},"resultModel":{"hasChild":1,"level":2,"menuId":666,"name":"裤子标签1","nid":"010601","productId":0,"remark":"","sequence":10699,"status":2,"type":"菜品"}},{"children":[],"nodeModel":{"hasChild":1,"level":2,"menuId":666,"name":"裤子标签2","nid":"010602","productId":0,"remark":"","sequence":10699,"status":2,"type":"菜品"},"resultModel":{"hasChild":1,"level":2,"menuId":666,"name":"裤子标签2","nid":"010602","productId":0,"remark":"","sequence":10699,"status":2,"type":"菜品"}},{"children":[],"nodeModel":{"hasChild":1,"level":2,"menuId":666,"name":"裤子标签3","nid":"010603","productId":0,"remark":"","sequence":10699,"status":2,"type":"菜品"},"resultModel":{"hasChild":1,"level":2,"menuId":666,"name":"裤子标签3","nid":"010603","productId":0,"remark":"","sequence":10699,"status":2,"type":"菜品"}}],"nodeModel":{"hasChild":1,"level":1,"menuId":666,"name":"裤子","nid":"0106","productId":0,"remark":"","sequence":199,"status":2,"type":"一级分类"},"resultModel":{"hasChild":1,"level":1,"menuId":666,"name":"裤子","nid":"0106","productId":0,"remark":"","sequence":199,"status":2,"type":"一级分类"}},{"children":[{"children":[],"nodeModel":{"hasChild":1,"level":2,"menuId":666,"name":"手环标签","nid":"010701","productId":0,"remark":"","sequence":10799,"status":2,"type":"菜品"},"resultModel":{"hasChild":1,"level":2,"menuId":666,"name":"手环标签","nid":"010701","productId":0,"remark":"","sequence":10799,"status":2,"type":"菜品"}}],"nodeModel":{"hasChild":1,"level":1,"menuId":666,"name":"手环","nid":"0107","productId":0,"remark":"","sequence":199,"status":2,"type":"一级分类"},"resultModel":{"hasChild":1,"level":1,"menuId":666,"name":"手环","nid":"0107","productId":0,"remark":"","sequence":199,"status":2,"type":"一级分类"}},{"children":[{"children":[],"nodeModel":{"hasChild":1,"level":2,"menuId":666,"name":"耳环标签","nid":"010801","productId":0,"remark":"","sequence":199,"status":2,"type":"一级分类"},"resultModel":{"hasChild":1,"level":2,"menuId":666,"name":"耳环标签","nid":"010801","productId":0,"remark":"","sequence":199,"status":2,"type":"一级分类"}}],"nodeModel":{"hasChild":1,"level":1,"menuId":666,"name":"耳环","nid":"0108","productId":0,"remark":"","sequence":199,"status":2,"type":"一级分类"},"resultModel":{"hasChild":1,"level":1,"menuId":666,"name":"耳环","nid":"0108","productId":0,"remark":"","sequence":199,"status":2,"type":"一级分类"}},{"children":[{"children":[],"nodeModel":{"hasChild":1,"level":2,"menuId":666,"name":"项链标签","nid":"010901","productId":0,"remark":"","sequence":199,"status":2,"type":"一级分类"},"resultModel":{"hasChild":1,"level":2,"menuId":666,"name":"项链标签","nid":"010901","productId":0,"remark":"","sequence":199,"status":2,"type":"一级分类"}}],"nodeModel":{"hasChild":1,"level":1,"menuId":666,"name":"项链","nid":"0109","productId":0,"remark":"","sequence":199,"status":2,"type":"一级分类"},"resultModel":{"hasChild":1,"level":1,"menuId":666,"name":"项链","nid":"0109","productId":0,"remark":"","sequence":199,"status":2,"type":"一级分类"}}]
     * errcode : 100
     * errmsg : 查询菜单下一级信息成功
     */

    public List<DataBean> data;

    public static class DataBean {
        /**
         * children : [{"children":[],"nodeModel":{"hasChild":1,"level":2,"menuId":666,"name":"鞋子标签","nid":"010101","productId":0,"remark":"","sequence":10199,"status":2,"type":"菜品"},"resultModel":{"hasChild":1,"level":2,"menuId":666,"name":"鞋子标签","nid":"010101","productId":0,"remark":"","sequence":10199,"status":2,"type":"菜品"}}]
         * nodeModel : {"hasChild":1,"level":1,"menuId":666,"name":"鞋","nid":"0101","productId":0,"remark":"","sequence":199,"status":2,"type":"一级分类"}
         * resultModel : {"hasChild":1,"level":1,"menuId":666,"name":"鞋","nid":"0101","productId":0,"remark":"","sequence":199,"status":2,"type":"一级分类"}
         */

        public NodeModelBean nodeModel;
        public ResultModelBean resultModel;
        public List<ChildrenBean> children;

        public static class NodeModelBean {
            /**
             * hasChild : 1
             * level : 1
             * menuId : 666
             * name : 鞋
             * nid : 0101
             * productId : 0
             * remark :
             * sequence : 199
             * status : 2
             * type : 一级分类
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
             * level : 1
             * menuId : 666
             * name : 鞋
             * nid : 0101
             * productId : 0
             * remark :
             * sequence : 199
             * status : 2
             * type : 一级分类
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
             * nodeModel : {"hasChild":1,"level":2,"menuId":666,"name":"鞋子标签","nid":"010101","productId":0,"remark":"","sequence":10199,"status":2,"type":"菜品"}
             * resultModel : {"hasChild":1,"level":2,"menuId":666,"name":"鞋子标签","nid":"010101","productId":0,"remark":"","sequence":10199,"status":2,"type":"菜品"}
             */

            public NodeModelBeanX nodeModel;
            public ResultModelBeanX resultModel;
            public List<?> children;

            public static class NodeModelBeanX {
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

            public static class ResultModelBeanX {
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
        }
    }
}
