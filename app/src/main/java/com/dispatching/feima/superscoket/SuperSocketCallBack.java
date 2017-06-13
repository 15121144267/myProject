package com.dispatching.feima.superscoket;

import com.dispatching.feima.service.CustomerService;

/**
 * Created by lei.he on 2017/6/13.
 */

public class SuperSocketCallBack implements ISocketPacket {
    private final CustomerService mCustomerService;

    public SuperSocketCallBack(CustomerService customerService) {
        this.mCustomerService = customerService;
    }

    @Override
    public void SocketPacket(String msg) {
        if (msg != null) {
            mCustomerService.transformSuperSocketInfo(msg);
        }
    }
}
