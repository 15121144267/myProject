package com.dispatching.feima.view.PresenterControl;

import com.dispatching.feima.entity.OrderConfirmedRequest;
import com.dispatching.feima.entity.OrderConfirmedResponse;
import com.dispatching.feima.entity.PayResponse;
import com.dispatching.feima.entity.SpecificationResponse;

/**
 * Created by lei.he on 2017/6/28.
 */

public class PayControl {
    public interface PayView extends LoadDataView {
        void orderConfirmedSuccess(OrderConfirmedResponse response);
        void orderPayInfoSuccess(PayResponse response);
        void orderPaySuccess();
    }

    public interface PresenterPay extends Presenter<PayView> {
        void requestOrderConfirmed(OrderConfirmedRequest request,SpecificationResponse.ProductsBean.ProductSpecificationBean productSpecification);
        void requestPayInfo(long oid,String payCode);
    }
}
