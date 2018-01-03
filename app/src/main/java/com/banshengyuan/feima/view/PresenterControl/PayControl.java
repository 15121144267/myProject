package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.AddressResponse;
import com.banshengyuan.feima.entity.MyCoupleResponse;
import com.banshengyuan.feima.entity.OrderConfirmItem;
import com.banshengyuan.feima.entity.OrderConfirmedResponse;

import java.util.List;

/**
 * Created by lei.he on 2017/6/28.
 * PayControl
 */

public class PayControl {
    public interface PayView extends LoadDataView {
        void orderConfirmedSuccess(OrderConfirmedResponse response);
        void listAddressSuccess(AddressResponse addressResponse);
        void getCouponListRequestSuccess(MyCoupleResponse response);
        void getCouponListRequestFail(String des);
    }

    public interface PresenterPay extends Presenter<PayView> {
        void requestOrderConfirmed(String addressId, List<OrderConfirmItem> list,Integer self);
        void requestAddressList(String token);
        void requestCouponList(String storeId,String status);
    }
}
