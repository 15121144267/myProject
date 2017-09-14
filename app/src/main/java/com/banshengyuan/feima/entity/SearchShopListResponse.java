package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/8/28.
 * SearchShopListResponse
 */

public class SearchShopListResponse implements Serializable{

    /**
     * statusCode : 100
     * msg : Success
     * data : [{"partnerId":"82133fac-4825-418b-be84-0d6a0310ae73","storeId":"99999","storeName":"测试门店","businessHours":"8:00-24:00","businessImages":[],"address":"","phone":"","longitude":"","latitude":"","remark":null}]
     */

    public List<DataBean> data;

    public static class DataBean {
        /**
         * partnerId : 82133fac-4825-418b-be84-0d6a0310ae73
         * storeId : 99999
         * storeName : 测试门店
         * businessHours : 8:00-24:00
         * businessImages : []
         * address :
         * phone :
         * longitude :
         * latitude :
         * remark : null
         */

        public String partnerId;
        public String storeId;
        public String storeName;
        public String businessHours;
        public String address;
        public String phone;
        public String longitude;
        public String latitude;
        public Object remark;
        public List<BusinessImagesBean> businessImages;

        public static class BusinessImagesBean implements Serializable{

            public int sortIndex;
            public String imageUrl;
            public String imageTitle;
        }
    }
}
