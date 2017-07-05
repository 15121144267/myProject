package com.dispatching.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/6/29.
 */

public class ShopListResponse implements Serializable {

    /**
     * pageCount : 1
     * pageNo : 1
     * count : 3
     * list : [{"address":"å\u008d\u0097äº\u008cç\u008e¯ä¸\u008eå®¿æ\u009d¾è·¯äº¤å\u008f£ç»¿å\u009c°ä¸­å¿\u0083å\u008d\u008aç\u0094\u009fç¼\u0098è¡\u0097å\u008cº","briefName":"","latitude":"37.262222","businessHours":"8:00-24:00","fullName":"LET  ME  SEE","active":"æ\u009c\u0089æ\u0095\u0088","remark":"","deliveryPrice":0,"typeFlag":3,"phone":"13856037856","storeName":"LET  ME  SEE","businessImages":[{"sortIndex":0,"imageUrl":"http://7xrib0.com2.z0.glb.qiniucdn.com/201706/1498641170074.jpg","imageTitle":""}],"deliveryRadius":0,"storeCode":"107","longitude":"111.768689"},{"address":"","briefName":"","latitude":"","businessHours":"8:00-24:00","fullName":"æµ\u008bè¯\u0095åº\u0097","active":"æ\u009c\u0089æ\u0095\u0088","remark":"","deliveryPrice":0,"typeFlag":3,"phone":"","storeName":"æµ\u008bè¯\u0095åº\u0097","businessImages":[],"deliveryRadius":0,"storeCode":"test01","longitude":""},{"address":"å\u008d\u0097äº\u008cç\u008e¯ä¸\u008eå®¿æ\u009d¾è·¯äº¤å\u008f£ç»¿å\u009c°ä¸­å¿\u0083å\u008d\u008aç\u0094\u009fç¼\u0098è¡\u0097å\u008cº","briefName":"","latitude":"37.262222","businessHours":"8:00-24:00","fullName":"å¤§å\u0088\u009bç\u0094\u009fæ´»é¦\u0086","active":"æ\u009c\u0089æ\u0095\u0088","remark":"","deliveryPrice":0,"typeFlag":3,"phone":"18010879919","storeName":"å¤§å\u0088\u009bç\u0094\u009fæ´»é¦\u0086","businessImages":[{"sortIndex":0,"imageUrl":"http://7xrib0.com2.z0.glb.qiniucdn.com/201706/1498641142197.jpg","imageTitle":""}],"deliveryRadius":0,"storeCode":"001","longitude":"111.768689"}]
     */

    public int pageCount;
    public int pageNo;
    public int count;
    public List<ListBean> list;

    public static class ListBean {
        /**
         * address : åäºç¯ä¸å®¿æ¾è·¯äº¤å£ç»¿å°ä¸­å¿åçç¼è¡åº
         * briefName :
         * latitude : 37.262222
         * businessHours : 8:00-24:00
         * fullName : LET  ME  SEE
         * active : ææ
         * remark :
         * deliveryPrice : 0
         * typeFlag : 3
         * phone : 13856037856
         * storeName : LET  ME  SEE
         * businessImages : [{"sortIndex":0,"imageUrl":"http://7xrib0.com2.z0.glb.qiniucdn.com/201706/1498641170074.jpg","imageTitle":""}]
         * deliveryRadius : 0
         * storeCode : 107
         * longitude : 111.768689
         */

        public String address;
        public String briefName;
        public String latitude;
        public String businessHours;
        public String fullName;
        public String active;
        public String remark;
        public int deliveryPrice;
        public int typeFlag;
        public String phone;
        public String storeName;
        public int deliveryRadius;
        public String storeCode;
        public String longitude;
        public List<BusinessImagesBean> businessImages;

        public static class BusinessImagesBean {
            /**
             * sortIndex : 0
             * imageUrl : http://7xrib0.com2.z0.glb.qiniucdn.com/201706/1498641170074.jpg
             * imageTitle :
             */

            public int sortIndex;
            public String imageUrl;
            public String imageTitle;
        }
    }
}
