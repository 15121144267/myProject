package com.banshengyuan.feima.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by lei.he on 2017/6/28.
 * AddressResponse
 */

public class AddressResponse implements Parcelable{

    private List<ListBean> list;

    protected AddressResponse(Parcel in) {
        list = in.createTypedArrayList(ListBean.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(list);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AddressResponse> CREATOR = new Creator<AddressResponse>() {
        @Override
        public AddressResponse createFromParcel(Parcel in) {
            return new AddressResponse(in);
        }

        @Override
        public AddressResponse[] newArray(int size) {
            return new AddressResponse[size];
        }
    };

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Parcelable{
        /**
         * id : 4
         * name : test
         * mobile : 13813800000
         * province : 上海市
         * city : 上海
         * area : 徐汇区
         * street : 虹漕路接到
         * address : 人民广场
         * is_default : 1
         */

        private int id;
        private String name;
        private String mobile;
        private String province;
        private String city;
        private String area;
        private String street;
        private String address;
        private int is_default;

        protected ListBean(Parcel in) {
            id = in.readInt();
            name = in.readString();
            mobile = in.readString();
            province = in.readString();
            city = in.readString();
            area = in.readString();
            street = in.readString();
            address = in.readString();
            is_default = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(name);
            dest.writeString(mobile);
            dest.writeString(province);
            dest.writeString(city);
            dest.writeString(area);
            dest.writeString(street);
            dest.writeString(address);
            dest.writeInt(is_default);
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

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getIs_default() {
            return is_default;
        }

        public void setIs_default(int is_default) {
            this.is_default = is_default;
        }
    }
}
