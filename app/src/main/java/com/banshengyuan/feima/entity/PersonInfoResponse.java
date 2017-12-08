package com.banshengyuan.feima.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lei.he on 2017/7/10.
 * PersonInfoResponse
 */

public class PersonInfoResponse implements Parcelable {

    /**
     * info : {"id":1,"token":"MSwxNTEwMjEzNjYyLDRkMDdlNTVjZTdjYjc1Y2EwMjBkZjJjNmIxNTZhNWQ3NTllODIxNWU=","token_expire":"2018-01-31 14:16:25","name":"test123","mobile":"138123341234","password":"4a6629303c679cfa6a5a81433743e52c","salt":"fsagawg#$#fsfsdffdadasd","reg_source":1,"head_img":"","sex":1,"birthday":"2017-01-01","status":1,"created_at":"2017-11-09 15:38:54","updated_at":"2017-12-07 19:23:37"}
     */

    private InfoBean info;

    protected PersonInfoResponse(Parcel in) {
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

    public static final Creator<PersonInfoResponse> CREATOR = new Creator<PersonInfoResponse>() {
        @Override
        public PersonInfoResponse createFromParcel(Parcel in) {
            return new PersonInfoResponse(in);
        }

        @Override
        public PersonInfoResponse[] newArray(int size) {
            return new PersonInfoResponse[size];
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
         * id : 1
         * token : MSwxNTEwMjEzNjYyLDRkMDdlNTVjZTdjYjc1Y2EwMjBkZjJjNmIxNTZhNWQ3NTllODIxNWU=
         * token_expire : 2018-01-31 14:16:25
         * name : test123
         * mobile : 138123341234
         * password : 4a6629303c679cfa6a5a81433743e52c
         * salt : fsagawg#$#fsfsdffdadasd
         * reg_source : 1
         * head_img :
         * sex : 1
         * birthday : 2017-01-01
         * status : 1
         * created_at : 2017-11-09 15:38:54
         * updated_at : 2017-12-07 19:23:37
         */

        private int id;
        private String token;
        private String token_expire;
        private String name;
        private String mobile;
        private String password;
        private String salt;
        private int reg_source;
        private String head_img;
        private int sex;
        private String birthday;
        private int status;
        private String created_at;
        private String updated_at;

        protected InfoBean(Parcel in) {
            id = in.readInt();
            token = in.readString();
            token_expire = in.readString();
            name = in.readString();
            mobile = in.readString();
            password = in.readString();
            salt = in.readString();
            reg_source = in.readInt();
            head_img = in.readString();
            sex = in.readInt();
            birthday = in.readString();
            status = in.readInt();
            created_at = in.readString();
            updated_at = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(token);
            dest.writeString(token_expire);
            dest.writeString(name);
            dest.writeString(mobile);
            dest.writeString(password);
            dest.writeString(salt);
            dest.writeInt(reg_source);
            dest.writeString(head_img);
            dest.writeInt(sex);
            dest.writeString(birthday);
            dest.writeInt(status);
            dest.writeString(created_at);
            dest.writeString(updated_at);
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

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getToken_expire() {
            return token_expire;
        }

        public void setToken_expire(String token_expire) {
            this.token_expire = token_expire;
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

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getSalt() {
            return salt;
        }

        public void setSalt(String salt) {
            this.salt = salt;
        }

        public int getReg_source() {
            return reg_source;
        }

        public void setReg_source(int reg_source) {
            this.reg_source = reg_source;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }
}
