package com.dispatching.feima.view.PresenterControl;

import com.dispatching.feima.entity.DeliveryStatusResponse;
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
        void updateOrderStatusSuccess(DeliveryStatusResponse response);
    }

    public interface PresenterPendingOrder extends Presenter<PendingOrderView> {
        void requestPendingOrder(Integer position,String token,String version,String uId);
        void requestTakeOrder(String token,String version,String uId,String deliveryId);
    }
}
