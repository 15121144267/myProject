package com.dispatching.feima.view.PresenterControl;

import com.dispatching.feima.entity.OrderDeliveryResponse;

/**
 * Created by helei on 2017/5/3.
 */

public class SendingOrderControl {
    public interface SendingOrderView extends LoadDataView{
        void getSendingOrderSuccess(OrderDeliveryResponse response);
    }

    public interface PresenterSendingOrder extends Presenter<SendingOrderView> {
        void requestSendingOrder(Integer position,String token,String version,String uId);
    }
}
