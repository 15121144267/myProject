package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/7/14.
 * ShopResponse
 */

public class ShopResponse implements Serializable{


    /**
     * address : 南二环与宿松路交口绿地中心半生缘街区
     * briefName :
     * latitude : 37.262222
     * businessHours : 8:00-24:00
     * fullName : LET ME SEE
     * active : 有效
     * remark :
     * deliveryPrice : 0
     * typeFlag : 3
     * phone : 13856037856
     * storeName : LET ME SEE
     * freeDeliveryPrice : 0
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
    public int freeDeliveryPrice;
    public int deliveryRadius;
    public String storeCode;
    public String longitude;
    public List<BusinessImagesBean> businessImages;

    public static class BusinessImagesBean implements Serializable{
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
