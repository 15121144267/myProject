package com.dispatching.feima.view.PresenterControl;

import com.dispatching.feima.entity.DeliveryStatusResponse;
import com.dispatching.feima.entity.OrderDeliveryResponse;

/**
 * Created by helei on 2017/5/3.
 * SendingOrderControl
 */

public class SendingOrderControl {
    public interface SendingOrderView extends LoadDataView{
        void getSendingOrderSuccess(OrderDeliveryResponse response);
        void updateOrderStatusSuccess(DeliveryStatusResponse response);
        void getOrderComplete();
        void getOrderError(Throwable throwable);
    }

    public interface PresenterSendingOrder extends Presenter<SendingOrderView> {
        void requestSendingOrder(Integer position,String token,String version,String uId);
        void requestCompleteOrder(String token,String version,String uId,String deliveryId);
    }
}
