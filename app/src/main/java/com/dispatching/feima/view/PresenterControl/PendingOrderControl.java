package com.dispatching.feima.view.PresenterControl;

import com.dispatching.feima.entity.OrderDeliveryResponse;

/**
 * Created by helei on 2017/5/3.
 */

public class PendingOrderControl {
    public interface PendingOrderView extends LoadDataView{
        void getPendingOrderSuccess(OrderDeliveryResponse response);
    }

    public interface PresenterPendingOrder extends Presenter<PendingOrderView> {
        void requestPendingOrder(Integer position,String token,String version,String uId);
    }
}
