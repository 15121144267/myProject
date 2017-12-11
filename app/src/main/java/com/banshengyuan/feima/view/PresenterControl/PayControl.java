package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.AddressResponse;
import com.banshengyuan.feima.entity.OrderConfirmItem;
import com.banshengyuan.feima.entity.OrderConfirmedResponse;
import com.banshengyuan.feima.entity.PayAccessRequest;
import com.banshengyuan.feima.entity.PayResponse;

import java.util.List;

/**
 * Created by lei.he on 2017/6/28.
 * PayControl
 */

public class PayControl {
    public interface PayView extends LoadDataView {
        void orderConfirmedSuccess(OrderConfirmedResponse response);
        void orderPayInfoSuccess(PayResponse response);
        void orderPaySuccess();
        void updateOrderStatusSuccess();
        void listAddressSuccess(AddressResponse addressResponse);
    }

    public interface PresenterPay extends Presenter<PayView> {
        void requestOrderConfirmed(String addressId, List<OrderConfirmItem> list);
        void requestPayInfo(OrderConfirmedResponse response,String payCode);
        void updateOrderStatus(PayAccessRequest request);
        void requestAddressList(String token);
    }
}
