package com.dispatching.feima.view.PresenterControl;

import com.dispatching.feima.entity.OrderConfirmedResponse;
import com.dispatching.feima.entity.PayAccessRequest;
import com.dispatching.feima.entity.PayCreateRequest;
import com.dispatching.feima.entity.PayResponse;

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
    }

    public interface PresenterPay extends Presenter<PayView> {
        void requestOrderConfirmed(PayCreateRequest request);
        void requestPayInfo(OrderConfirmedResponse response,String payCode);
        void updateOrderStatus(PayAccessRequest request);
    }
}
