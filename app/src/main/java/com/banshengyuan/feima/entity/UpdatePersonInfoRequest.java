package com.banshengyuan.feima.entity;

/**
 * Created by lei.he on 2017/7/11.
 * UpdatePersonInfoRequest
 */

public class UpdatePersonInfoRequest {
    public int id;
    public String token;
    public String token_expire;
    public String name;
    public String mobile;
    public String password;
    public String salt;
    public int reg_source;
    public String head_img;
    public int sex;
    public String birthday;
    public int status;
    public String created_at;

    @Override
    public String toString() {
        return "UpdatePersonInfoRequest{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", token_expire='" + token_expire + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", reg_source='" + reg_source + '\'' +
                ", head_img='" + head_img + '\'' +
                ", sex=" + sex +
                ", birthday='" + birthday + '\'' +
                ", status='" + status + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }
}
