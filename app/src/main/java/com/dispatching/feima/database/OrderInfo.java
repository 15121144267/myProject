package com.dispatching.feima.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by helei on 2017/4/25.
 */
@Entity
public class OrderInfo {
    @Id
    private long id;
    @Property(nameInDb ="name")
    private String name;
    @Generated(hash = 1864870352)
    public OrderInfo(long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 1695813404)
    public OrderInfo() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
