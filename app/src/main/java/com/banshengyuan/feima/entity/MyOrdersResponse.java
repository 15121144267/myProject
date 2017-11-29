package com.banshengyuan.feima.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/6/28.
 * MyOrdersResponse
 */

public class MyOrdersResponse implements Parcelable {


    /**
     * list : [{"order_sn":201711052203112,"order_item":[{"store_name":"潮流衣铺","store_id":1,"product":[{"id":11,"name":"产品名字11","cover_img":"http=>//example.com/a2.png","price":"120","number":1},{"id":11,"name":"产品名字12","cover_img":"http=>//example.com/a2.png","price":"130","number":1}]},{"store_name":"潮流衣铺2","store_id":2,"product":[{"id":21,"name":"产品名字21","cover_img":"http=>//example.com/a2.png","price":"120","number":1}]}],"pay_status":1,"total_fee":"570","freight":"200"}]
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(current_page);
        dest.writeByte((byte) (has_next_page ? 1 : 0));
        dest.writeTypedList(list);
    }

    public static class ListBean implements Parcelable{
        /**
         * order_sn : 201711052203112
         * order_item : [{"store_name":"潮流衣铺","store_id":1,"product":[{"id":11,"name":"产品名字11","cover_img":"http=>//example.com/a2.png","price":"120","number":1},{"id":11,"name":"产品名字12","cover_img":"http=>//example.com/a2.png","price":"130","number":1}]},{"store_name":"潮流衣铺2","store_id":2,"product":[{"id":21,"name":"产品名字21","cover_img":"http=>//example.com/a2.png","price":"120","number":1}]}]
         * pay_status : 1
         * total_fee : 570
         * freight : 200
         */

        private long order_sn;
        private int pay_status;
        private String total_fee;
        private String freight;
        private List<OrderItemBean> order_item;

        protected ListBean(Parcel in) {
            order_sn = in.readLong();
            pay_status = in.readInt();
            total_fee = in.readString();
            freight = in.readString();
            order_item = in.createTypedArrayList(OrderItemBean.CREATOR);
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

        public long getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(long order_sn) {
            this.order_sn = order_sn;
        }

        public int getPay_status() {
            return pay_status;
        }

        public void setPay_status(int pay_status) {
            this.pay_status = pay_status;
        }

        public String getTotal_fee() {
            return total_fee;
        }

        public void setTotal_fee(String total_fee) {
            this.total_fee = total_fee;
        }

        public String getFreight() {
            return freight;
        }

        public void setFreight(String freight) {
            this.freight = freight;
        }

        public List<OrderItemBean> getOrder_item() {
            return order_item;
        }

        public void setOrder_item(List<OrderItemBean> order_item) {
            this.order_item = order_item;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(order_sn);
            dest.writeInt(pay_status);
            dest.writeString(total_fee);
            dest.writeString(freight);
            dest.writeTypedList(order_item);
        }

        public static class OrderItemBean implements Parcelable{
            /**
             * store_name : 潮流衣铺
             * store_id : 1
             * product : [{"id":11,"name":"产品名字11","cover_img":"http=>//example.com/a2.png","price":"120","number":1},{"id":11,"name":"产品名字12","cover_img":"http=>//example.com/a2.png","price":"130","number":1}]
             */

            private String store_name;
            private int store_id;
            private List<ProductBean> product;

            public OrderItemBean(Parcel in) {
                store_name = in.readString();
                store_id = in.readInt();
                product = in.createTypedArrayList(ProductBean.CREATOR);
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

            public OrderItemBean() {

            }

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

            public List<ProductBean> getProduct() {
                return product;
            }

            public void setProduct(List<ProductBean> product) {
                this.product = product;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(store_name);
                dest.writeInt(store_id);
                dest.writeTypedList(product);
            }

            public static class ProductBean implements Parcelable{
                /**
                 * id : 11
                 * name : 产品名字11
                 * cover_img : http=>//example.com/a2.png
                 * price : 120
                 * number : 1
                 */

                private int id;
                private String name;
                private String cover_img;
                private String price;
                private int number;

                protected ProductBean(Parcel in) {
                    id = in.readInt();
                    name = in.readString();
                    cover_img = in.readString();
                    price = in.readString();
                    number = in.readInt();
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeInt(id);
                    dest.writeString(name);
                    dest.writeString(cover_img);
                    dest.writeString(price);
                    dest.writeInt(number);
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

                public String getCover_img() {
                    return cover_img;
                }

                public void setCover_img(String cover_img) {
                    this.cover_img = cover_img;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public int getNumber() {
                    return number;
                }

                public void setNumber(int number) {
                    this.number = number;
                }
            }
        }
    }
}
