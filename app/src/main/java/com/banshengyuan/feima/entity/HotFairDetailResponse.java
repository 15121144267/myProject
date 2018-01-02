package com.banshengyuan.feima.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by li.liu on 2017/11/21.
 * 热闹详情
 */

public class HotFairDetailResponse implements Parcelable{

    /**
     * info : {"id":4,"name":"就是这么热闹","cover_img":"http://ssapp.jixuanjk.com/uploads/5a2518f3eb4d160514.jpg","start_time":1512230400,"end_time":1513872000,"summary":null,"street_id":0,"street_name":"","content":"<p>这是个非常热闹的地方<\/p>","sales_type":2,"sales_price":11100,"order_sn":""}
     */

    private InfoBean info;

    protected HotFairDetailResponse(Parcel in) {
        info = in.readParcelable(InfoBean.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(info, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HotFairDetailResponse> CREATOR = new Creator<HotFairDetailResponse>() {
        @Override
        public HotFairDetailResponse createFromParcel(Parcel in) {
            return new HotFairDetailResponse(in);
        }

        @Override
        public HotFairDetailResponse[] newArray(int size) {
            return new HotFairDetailResponse[size];
        }
    };

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean implements Parcelable{
        /**
         * id : 4
         * name : 就是这么热闹
         * cover_img : http://ssapp.jixuanjk.com/uploads/5a2518f3eb4d160514.jpg
         * start_time : 1512230400
         * end_time : 1513872000
         * summary : null
         * street_id : 0
         * street_name :
         * content : <p>这是个非常热闹的地方</p>
         * sales_type : 2
         * sales_price : 11100
         * order_sn :
         * is_collection
         */

        private int id;
        private String name;
        private String cover_img;
        private int start_time;
        private int end_time;
        private Object summary;
        private int street_id;
        private String street_name;
        private String content;
        private int sales_type;
        private int sales_price;
        private String order_sn;
        private int is_collection;

        public int getIs_collection() {
            return is_collection;
        }

        public void setIs_collection(int is_collection) {
            this.is_collection = is_collection;
        }

        protected InfoBean(Parcel in) {
            id = in.readInt();
            name = in.readString();
            cover_img = in.readString();
            start_time = in.readInt();
            end_time = in.readInt();
            street_id = in.readInt();
            street_name = in.readString();
            content = in.readString();
            sales_type = in.readInt();
            sales_price = in.readInt();
            order_sn = in.readString();
            is_collection = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(name);
            dest.writeString(cover_img);
            dest.writeInt(start_time);
            dest.writeInt(end_time);
            dest.writeInt(street_id);
            dest.writeString(street_name);
            dest.writeString(content);
            dest.writeInt(sales_type);
            dest.writeInt(sales_price);
            dest.writeString(order_sn);
            dest.writeInt(is_collection);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<InfoBean> CREATOR = new Creator<InfoBean>() {
            @Override
            public InfoBean createFromParcel(Parcel in) {
                return new InfoBean(in);
            }

            @Override
            public InfoBean[] newArray(int size) {
                return new InfoBean[size];
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

        public int getStart_time() {
            return start_time;
        }

        public void setStart_time(int start_time) {
            this.start_time = start_time;
        }

        public int getEnd_time() {
            return end_time;
        }

        public void setEnd_time(int end_time) {
            this.end_time = end_time;
        }

        public Object getSummary() {
            return summary;
        }

        public void setSummary(Object summary) {
            this.summary = summary;
        }

        public int getStreet_id() {
            return street_id;
        }

        public void setStreet_id(int street_id) {
            this.street_id = street_id;
        }

        public String getStreet_name() {
            return street_name;
        }

        public void setStreet_name(String street_name) {
            this.street_name = street_name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getSales_type() {
            return sales_type;
        }

        public void setSales_type(int sales_type) {
            this.sales_type = sales_type;
        }

        public int getSales_price() {
            return sales_price;
        }

        public void setSales_price(int sales_price) {
            this.sales_price = sales_price;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }
    }
}
