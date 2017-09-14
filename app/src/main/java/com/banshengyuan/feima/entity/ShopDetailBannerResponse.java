package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/8/28.
 * ShopDetailBannerResponse
 */

public class ShopDetailBannerResponse implements Serializable {

    /**
     * statusCode : 100
     * msg : Success
     * data : [{"title":"门店轮播活动1","imageUrl":"http://7xrib0.com2.z0.glb.qiniucdn.com/201708/1503889614710.jpg","content":"","status":2,"partnerId":"82133fac-4825-418b-be84-0d6a0310ae73","createTime":"2017-08-28 11:06:56","sortIndex":"A001","startTime":"2017-08-28 00:00:00","endTime":"2017-10-01 00:00:00"},{"title":"门店轮播活动2","imageUrl":"http://7xrib0.com2.z0.glb.qiniucdn.com/201708/1503889644503.png","content":"","status":2,"partnerId":"82133fac-4825-418b-be84-0d6a0310ae73","createTime":"2017-08-28 11:07:26","sortIndex":"A002","startTime":"2017-08-28 00:00:00","endTime":"2017-10-01 00:00:00"}]
     */

    public List<DataBean> data;

    public static class DataBean {
        /**
         * title : 门店轮播活动1
         * imageUrl : http://7xrib0.com2.z0.glb.qiniucdn.com/201708/1503889614710.jpg
         * content :
         * status : 2
         * partnerId : 82133fac-4825-418b-be84-0d6a0310ae73
         * createTime : 2017-08-28 11:06:56
         * sortIndex : A001
         * startTime : 2017-08-28 00:00:00
         * endTime : 2017-10-01 00:00:00
         */

        public String title;
        public String imageUrl;
        public String content;
        public int status;
        public String partnerId;
        public String createTime;
        public String sortIndex;
        public String startTime;
        public String endTime;
    }
}
