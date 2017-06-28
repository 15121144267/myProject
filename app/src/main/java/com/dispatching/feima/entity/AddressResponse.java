package com.dispatching.feima.entity;

import java.io.Serializable;

/**
 * Created by lei.he on 2017/6/28.
 */

public class AddressResponse implements Serializable {
    public String name;
    public String phone;
    public String address;
    public boolean checkedAddress = false;
}
