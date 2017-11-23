package com.banshengyuan.feima.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by li.liu on 2017/11/21.
 * 热闹详情
 */

public class HotFairDetailResponse implements Parcelable{

    /**
     * info : {"id":1,"name":"热闹1名字","cover_img":"http=>//example.com/a1.png","start_time":"1510036420","end_time":"1510046420","summary":"热闹1的简介","street_id":1,"street_name":"街区名字","content":"<div> 热闹内容 <\/div>","sales_type":2,"sales_price":"129"}
     */

    private InfoBean info;

    protected HotFairDetailResponse(Parcel in) {
        info = in.readParcelable(InfoBean.class.getClassLoader());
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(info, flags);
    }

    public static class InfoBean implements Parcelable{
        /**
         * id : 1
         * name : 热闹1名字
         * cover_img : http=>//example.com/a1.png
         * start_time : 1510036420
         * end_time : 1510046420
         * summary : 热闹1的简介
         * street_id : 1
         * street_name : 街区名字
         * content : <div> 热闹内容 </div>
         * sales_type : 2
         * sales_price : 129
         */

        private int id;
        private String name;
        private String cover_img;
        private String start_time;
        private String end_time;
        private String summary;
        private int street_id;
        private String street_name;
        private String content;
        private int sales_type;
        private String sales_price;

        protected InfoBean(Parcel in) {
            id = in.readInt();
            name = in.readString();
            cover_img = in.readString();
            start_time = in.readString();
            end_time = in.readString();
            summary = in.readString();
            street_id = in.readInt();
            street_name = in.readString();
            content = in.readString();
            sales_type = in.readInt();
            sales_price = in.readString();
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

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
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

        public String getSales_price() {
            return sales_price;
        }

        public void setSales_price(String sales_price) {
            this.sales_price = sales_price;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(name);
            dest.writeString(cover_img);
            dest.writeString(start_time);
            dest.writeString(end_time);
            dest.writeString(summary);
            dest.writeInt(street_id);
            dest.writeString(street_name);
            dest.writeString(content);
            dest.writeInt(sales_type);
            dest.writeString(sales_price);
        }
    }
}
