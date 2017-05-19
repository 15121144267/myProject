package com.dispatching.feima.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;

/**
 * Created by helei on 2017/5/11.
 * OrderNotice
 */
@Entity
public class OrderNotice {
    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "orderId")
    private String orderId;

    @Property(nameInDb = "orderTime")
    private Date orderTime;

    @Property(nameInDb = "orderFlag")
    private Integer orderFlag;

    @Generated(hash = 40799993)
    public OrderNotice(Long id, String orderId, Date orderTime, Integer orderFlag) {
        this.id = id;
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.orderFlag = orderFlag;
    }

    @Generated(hash = 1687600177)
    public OrderNotice() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getOrderTime() {
        return this.orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getOrderFlag() {
        return this.orderFlag;
    }

    public void setOrderFlag(Integer orderFlag) {
        this.orderFlag = orderFlag;
    }

}
