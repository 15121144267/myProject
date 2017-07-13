package com.dispatching.feima.view.PresenterControl;

import com.dispatching.feima.entity.OrderConfirmedRequest;
import com.dispatching.feima.entity.OrderConfirmedResponse;

/**
 * Created by lei.he on 2017/6/28.
 */

public class PayControl {
    public interface PayView extends LoadDataView {
        void orderConfirmedSuccess(OrderConfirmedResponse response);
    }

    public interface PresenterPay extends Presenter<PayView> {
        void requestOrderConfirmed(OrderConfirmedRequest request);
    }
}
