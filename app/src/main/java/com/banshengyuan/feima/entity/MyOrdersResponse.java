package com.banshengyuan.feima.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by lei.he on 2017/6/28.
 * MyOrdersResponse
 */

public class MyOrdersResponse implements Parcelable {


    /**
     * list : [{"order_sn":"2017120105481148287","order_item":[{"sub_sn":"Z2017120105481185081","store_id":8,"store_name":"test的店铺","product":[{"id":1,"name":"测试商品","goods_id":1,"price":10000,"number":1,"cover_img":"[\"/upload/example.jpg\", \"/upload/example2.jpg\", \"/upload/example3.jpg\", \"/upload/example4.jpg\"]"},{"id":2,"name":"测试商品","goods_id":1,"price":11000,"number":1,"cover_img":"[\"/upload/example.jpg\", \"/upload/example2.jpg\", \"/upload/example3.jpg\", \"/upload/example4.jpg\"]"}]}],"pay_status":1,"total_fee":21000,"freight":0},{"order_sn":"2017120105505194448","order_item":[{"sub_sn":"Z2017120105505111098","store_id":8,"store_name":"test的店铺","product":[{"id":3,"name":"测试商品","goods_id":1,"price":12000,"number":1,"cover_img":"[\"/upload/example.jpg\", \"/upload/example2.jpg\", \"/upload/example3.jpg\", \"/upload/example4.jpg\"]"},{"id":6,"name":"测试商品","goods_id":1,"price":15000,"number":1,"cover_img":"[\"/upload/example.jpg\", \"/upload/example2.jpg\", \"/upload/example3.jpg\", \"/upload/example4.jpg\"]"},{"id":7,"name":"测试商品","goods_id":1,"price":15000,"number":1,"cover_img":"[\"/upload/example.jpg\", \"/upload/example2.jpg\", \"/upload/example3.jpg\", \"/upload/example4.jpg\"]"}]}],"pay_status":1,"total_fee":42000,"freight":0}]
     * current_page : 1
     * has_next_page : true
     */

    private int current_page;
    private boolean has_next_page;
    private List<ListBean> list;


    protected MyOrdersResponse(Parcel in) {
        current_page = in.readInt();
        has_next_page = in.readByte() != 0;
        list = in.createTypedArrayList(ListBean.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(current_page);
        dest.writeByte((byte) (has_next_page ? 1 : 0));
        dest.writeTypedList(list);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MyOrdersResponse> CREATOR = new Creator<MyOrdersResponse>() {
        @Override
        public MyOrdersResponse createFromParcel(Parcel in) {
            return new MyOrdersResponse(in);
        }

        @Override
        public MyOrdersResponse[] newArray(int size) {
            return new MyOrdersResponse[size];
        }
    };

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public boolean isHas_next_page() {
        return has_next_page;
    }

    public void setHas_next_page(boolean has_next_page) {
        this.has_next_page = has_next_page;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Parcelable{
        /**
         * order_sn : 2017120105481148287
         * order_item : [{"sub_sn":"Z2017120105481185081","store_id":8,"store_name":"test的店铺","product":[{"id":1,"name":"测试商品","goods_id":1,"price":10000,"number":1,"cover_img":"[\"/upload/example.jpg\", \"/upload/example2.jpg\", \"/upload/example3.jpg\", \"/upload/example4.jpg\"]"},{"id":2,"name":"测试商品","goods_id":1,"price":11000,"number":1,"cover_img":"[\"/upload/example.jpg\", \"/upload/example2.jpg\", \"/upload/example3.jpg\", \"/upload/example4.jpg\"]"}]}]
         * pay_status : 1
         * total_fee : 21000
         * freight : 0
         */

        private String order_sn;
        private int pay_status;
        private int total_fee;
        private int freight;
        private List<OrderItemBean> order_item;


        protected ListBean(Parcel in) {
            order_sn = in.readString();
            pay_status = in.readInt();
            total_fee = in.readInt();
            freight = in.readInt();
            order_item = in.createTypedArrayList(OrderItemBean.CREATOR);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(order_sn);
            dest.writeInt(pay_status);
            dest.writeInt(total_fee);
            dest.writeInt(freight);
            dest.writeTypedList(order_item);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
            @Override
            public ListBean createFromParcel(Parcel in) {
                return new ListBean(in);
            }

            @Override
            public ListBean[] newArray(int size) {
                return new ListBean[size];
            }
        };

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public int getPay_status() {
            return pay_status;
        }

        public void setPay_status(int pay_status) {
            this.pay_status = pay_status;
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

        public List<OrderItemBean> getOrder_item() {
            return order_item;
        }

        public void setOrder_item(List<OrderItemBean> order_item) {
            this.order_item = order_item;
        }

        public static class OrderItemBean implements Parcelable{
            /**
             * sub_sn : Z2017120105481185081
             * store_id : 8
             * store_name : test的店铺
             * product : [{"id":1,"name":"测试商品","goods_id":1,"price":10000,"number":1,"cover_img":"[\"/upload/example.jpg\", \"/upload/example2.jpg\", \"/upload/example3.jpg\", \"/upload/example4.jpg\"]"},{"id":2,"name":"测试商品","goods_id":1,"price":11000,"number":1,"cover_img":"[\"/upload/example.jpg\", \"/upload/example2.jpg\", \"/upload/example3.jpg\", \"/upload/example4.jpg\"]"}]
             */

            private String sub_sn;
            private int store_id;
            private String store_name;
            private List<ProductBean> product;


            protected OrderItemBean(Parcel in) {
                sub_sn = in.readString();
                store_id = in.readInt();
                store_name = in.readString();
                product = in.createTypedArrayList(ProductBean.CREATOR);
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(sub_sn);
                dest.writeInt(store_id);
                dest.writeString(store_name);
                dest.writeTypedList(product);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public static final Creator<OrderItemBean> CREATOR = new Creator<OrderItemBean>() {
                @Override
                public OrderItemBean createFromParcel(Parcel in) {
                    return new OrderItemBean(in);
                }

                @Override
                public OrderItemBean[] newArray(int size) {
                    return new OrderItemBean[size];
                }
            };

            public String getSub_sn() {
                return sub_sn;
            }

            public void setSub_sn(String sub_sn) {
                this.sub_sn = sub_sn;
            }

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }

            public String getStore_name() {
                return store_name;
            }

            public void setStore_name(String store_name) {
                this.store_name = store_name;
            }

            public List<ProductBean> getProduct() {
                return product;
            }

            public void setProduct(List<ProductBean> product) {
                this.product = product;
            }

            public static class ProductBean implements Parcelable{
                /**
                 * id : 1
                 * name : 测试商品
                 * goods_id : 1
                 * price : 10000
                 * number : 1
                 * cover_img : ["/upload/example.jpg", "/upload/example2.jpg", "/upload/example3.jpg", "/upload/example4.jpg"]
                 */

                private int id;
                private String name;
                private int goods_id;
                private int price;
                private int number;
                private String cover_img;

                protected ProductBean(Parcel in) {
                    id = in.readInt();
                    name = in.readString();
                    goods_id = in.readInt();
                    price = in.readInt();
                    number = in.readInt();
                    cover_img = in.readString();
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeInt(id);
                    dest.writeString(name);
                    dest.writeInt(goods_id);
                    dest.writeInt(price);
                    dest.writeInt(number);
                    dest.writeString(cover_img);
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                public static final Creator<ProductBean> CREATOR = new Creator<ProductBean>() {
                    @Override
                    public ProductBean createFromParcel(Parcel in) {
                        return new ProductBean(in);
                    }

                    @Override
                    public ProductBean[] newArray(int size) {
                        return new ProductBean[size];
                    }
                };

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getGoods_id() {
                    return goods_id;
                }

                public void setGoods_id(int goods_id) {
                    this.goods_id = goods_id;
                }

                public int getPrice() {
                    return price;
                }

                public void setPrice(int price) {
                    this.price = price;
                }

                public int getNumber() {
                    return number;
                }

                public void setNumber(int number) {
                    this.number = number;
                }

                public String getCover_img() {
                    return cover_img;
                }

                public void setCover_img(String cover_img) {
                    this.cover_img = cover_img;
                }
            }
        }
    }
}
