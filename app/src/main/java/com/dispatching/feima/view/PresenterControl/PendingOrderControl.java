package com.dispatching.feima.view.PresenterControl;

import com.dispatching.feima.entity.OrderDeliveryResponse;

/**
 * Created by helei on 2017/5/3.
 * PendingOrderControl
 */

public class PendingOrderControl {
    public interface PendingOrderView extends LoadDataView{
        void getPendingOrderSuccess(OrderDeliveryResponse response);
        void getPendingOrderComplete();
        void getOrderError(Throwable throwable);
        void updateOrderStatusSuccess();
    }

    public interface PresenterPendingOrder extends Presenter<PendingOrderView> {
        void requestPendingOrder(String token, String uId);
        void requestTakeOrder(String token, String uId, String deliveryId);
        void requestUpOrder(String businessId);
    }
}
