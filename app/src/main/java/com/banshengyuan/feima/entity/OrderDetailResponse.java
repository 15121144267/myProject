package com.banshengyuan.feima.entity;

import java.util.List;

/**
 * Created by li.liu on 2017/12/11.
 */

public class OrderDetailResponse {


    /**
     * info : {"member_name":"","member_mobile":"","member_province":"","member_city":"","member_area":"","member_street":"","member_address":"","sn":"2017122504384835689","payed":0,"total_fee":12600,"freight":0,"create_time":1514191128,"deal_time":1514191139,"pay_status":3,"deliver_status":1,"receive_status":1,"comment_status":2,"comment_content":"潮流衣铺","logistics_name":"","logistics_sn":"","status":"交易关闭","order_type":2,"selffetch_code":""}
     * goods_list : {"store_name":"test的店铺","store_id":8,"store_mobile":"15000319679","product":[{"goods_id":87,"sku_name":"1206","goods_name":"测试1206","number":1,"id":1,"goods_sku":"1206","goods_img":"http://ssapp.jixuanjk.com/upload/5a27c04cc812b24931.jpg","goods_price":12600}]}
     */

    private InfoBean info;
    private GoodsListBean goods_list;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public GoodsListBean getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(GoodsListBean goods_list) {
        this.goods_list = goods_list;
    }

    public static class InfoBean {
        /**
         * member_name :
         * member_mobile :
         * member_province :
         * member_city :
         * member_area :
         * member_street :
         * member_address :
         * sn : 2017122504384835689
         * payed : 0
         * total_fee : 12600
         * freight : 0
         * create_time : 1514191128
         * deal_time : 1514191139
         * pay_status : 3
         * deliver_status : 1
         * receive_status : 1
         * comment_status : 2
         * comment_content : 潮流衣铺
         * logistics_name :
         * logistics_sn :
         * status : 交易关闭
         * order_type : 2
         * selffetch_code :
         */

        private String member_name;
        private String member_mobile;
        private String member_province;
        private String member_city;
        private String member_area;
        private String member_street;
        private String member_address;
        private String sn;
        private int payed;
        private int total_fee;
        private int freight;
        private int create_time;
        private int deal_time;
        private int pay_status;
        private int deliver_status;
        private int receive_status;
        private int comment_status;
        private String comment_content;
        private String logistics_name;
        private String logistics_sn;
        private String status;
        private int order_type;
        private String selffetch_code;

        public String getMember_name() {
            return member_name;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }

        public String getMember_mobile() {
            return member_mobile;
        }

        public void setMember_mobile(String member_mobile) {
            this.member_mobile = member_mobile;
        }

        public String getMember_province() {
            return member_province;
        }

        public void setMember_province(String member_province) {
            this.member_province = member_province;
        }

        public String getMember_city() {
            return member_city;
        }

        public void setMember_city(String member_city) {
            this.member_city = member_city;
        }

        public String getMember_area() {
            return member_area;
        }

        public void setMember_area(String member_area) {
            this.member_area = member_area;
        }

        public String getMember_street() {
            return member_street;
        }

        public void setMember_street(String member_street) {
            this.member_street = member_street;
        }

        public String getMember_address() {
            return member_address;
        }

        public void setMember_address(String member_address) {
            this.member_address = member_address;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public int getPayed() {
            return payed;
        }

        public void setPayed(int payed) {
            this.payed = payed;
        }

        public int getTotal_fee() {
            return total_fee;
        }

        public void setTotal_fee(int total_fee) {
            this.total_fee = total_fee;
        }

        public int getFreight() {
            return freight;
        }

        public void setFreight(int freight) {
            this.freight = freight;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getDeal_time() {
            return deal_time;
        }

        public void setDeal_time(int deal_time) {
            this.deal_time = deal_time;
        }

        public int getPay_status() {
            return pay_status;
        }

        public void setPay_status(int pay_status) {
            this.pay_status = pay_status;
        }

        public int getDeliver_status() {
            return deliver_status;
        }

        public void setDeliver_status(int deliver_status) {
            this.deliver_status = deliver_status;
        }

        public int getReceive_status() {
            return receive_status;
        }

        public void setReceive_status(int receive_status) {
            this.receive_status = receive_status;
        }

        public int getComment_status() {
            return comment_status;
        }

        public void setComment_status(int comment_status) {
            this.comment_status = comment_status;
        }

        public String getComment_content() {
            return comment_content;
        }

        public void setComment_content(String comment_content) {
            this.comment_content = comment_content;
        }

        public String getLogistics_name() {
            return logistics_name;
        }

        public void setLogistics_name(String logistics_name) {
            this.logistics_name = logistics_name;
        }

        public String getLogistics_sn() {
            return logistics_sn;
        }

        public void setLogistics_sn(String logistics_sn) {
            this.logistics_sn = logistics_sn;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getOrder_type() {
            return order_type;
        }

        public void setOrder_type(int order_type) {
            this.order_type = order_type;
        }

        public String getSelffetch_code() {
            return selffetch_code;
        }

        public void setSelffetch_code(String selffetch_code) {
            this.selffetch_code = selffetch_code;
        }
    }

    public static class GoodsListBean {
        /**
         * store_name : test的店铺
         * store_id : 8
         * store_mobile : 15000319679
         * product : [{"goods_id":87,"sku_name":"1206","goods_name":"测试1206","number":1,"id":1,"goods_sku":"1206","goods_img":"http://ssapp.jixuanjk.com/upload/5a27c04cc812b24931.jpg","goods_price":12600}]
         */

        private String store_name;
        private int store_id;
        private String store_mobile;
        private List<ProductBean> product;

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public int getStore_id() {
            return store_id;
        }

        public void setStore_id(int store_id) {
            this.store_id = store_id;
        }

        public String getStore_mobile() {
            return store_mobile;
        }

        public void setStore_mobile(String store_mobile) {
            this.store_mobile = store_mobile;
        }

        public List<ProductBean> getProduct() {
            return product;
        }

        public void setProduct(List<ProductBean> product) {
            this.product = product;
        }

        public static class ProductBean {
            /**
             * goods_id : 87
             * sku_name : 1206
             * goods_name : 测试1206
             * number : 1
             * id : 1
             * goods_sku : 1206
             * goods_img : http://ssapp.jixuanjk.com/upload/5a27c04cc812b24931.jpg
             * goods_price : 12600
             */

            private int goods_id;
            private String sku_name;
            private String goods_name;
            private int number;
            private int id;
            private String goods_sku;
            private String goods_img;
            private int goods_price;

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public String getSku_name() {
                return sku_name;
            }

            public void setSku_name(String sku_name) {
                this.sku_name = sku_name;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getGoods_sku() {
                return goods_sku;
            }

            public void setGoods_sku(String goods_sku) {
                this.goods_sku = goods_sku;
            }

            public String getGoods_img() {
                return goods_img;
            }

            public void setGoods_img(String goods_img) {
                this.goods_img = goods_img;
            }

            public int getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(int goods_price) {
                this.goods_price = goods_price;
            }
        }
    }
}
