package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/6/28.
 * AddressResponse
 */

public class AddressResponse implements Serializable {

    /**
     * statusCode : 100
     * msg : Success
     * data : [{"id":4,"partnerId":"a8bee0dd-09d1-4fa9-a9eb-80cb36d3d611","receiverName":null,"receiverPhone":"18915966899","phone":"18915966899","area":" 江苏-南京-建邺区","address":"梦都大街51号","isDefault":0},{"id":5,"partnerId":"a8bee0dd-09d1-4fa9-a9eb-80cb36d3d611","receiverName":null,"receiverPhone":"18915966899","phone":"18915966899","area":" 江苏-南京-建邺区","address":"梦都大街51号","isDefault":0},{"id":6,"partnerId":"a8bee0dd-09d1-4fa9-a9eb-80cb36d3d611","receiverName":null,"receiverPhone":"18915966899","phone":"18915966899","area":" 江苏-南京-建邺区","address":"梦都大街51号","isDefault":0},{"id":7,"partnerId":"a8bee0dd-09d1-4fa9-a9eb-80cb36d3d611","receiverName":null,"receiverPhone":"18915966899","phone":"18915966899","area":" 江苏-南京-建邺区","address":"梦都大街51号","isDefault":0},{"id":8,"partnerId":"a8bee0dd-09d1-4fa9-a9eb-80cb36d3d611","receiverName":null,"receiverPhone":"18915966899","phone":"18915966899","area":" 江苏-南京-建邺区","address":"梦都大街99号","isDefault":0},{"id":9,"partnerId":"a8bee0dd-09d1-4fa9-a9eb-80cb36d3d611","receiverName":null,"receiverPhone":"18915966899","phone":"18915966899","area":" 江苏-南京-建邺区","address":"梦都大街99号","isDefault":0},{"id":10,"partnerId":"a8bee0dd-09d1-4fa9-a9eb-80cb36d3d611","receiverName":null,"receiverPhone":"18915966899","phone":"18915966899","area":" 江苏-南京-建邺区","address":"梦都大街122号","isDefault":0}]
     */

    public List<DataBean> data;

    public static class DataBean implements Serializable{
        /**
         * id : 4
         * partnerId : a8bee0dd-09d1-4fa9-a9eb-80cb36d3d611
         * receiverName : null
         * receiverPhone : 18915966899
         * phone : 18915966899
         * area :  江苏-南京-建邺区
         * address : 梦都大街51号
         * isDefault : 0
         */

        public int id;
        public String partnerId;
        public Object receiverName;
        public String receiverPhone;
        public String phone;
        public String area;
        public String address;
        public int isDefault;
    }
}
