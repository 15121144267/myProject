package com.dispatching.feima.entity;

import java.io.Serializable;

/**
 * Created by lei.he on 2017/7/10.
 */

public class PersonInfoResponse implements Serializable {
    public String memberId;
    public String avatarUrl;
    public String nickName;
    public Integer sex = 0;
    public String phone;
    public String birthday;
    public String partnerId;
}
