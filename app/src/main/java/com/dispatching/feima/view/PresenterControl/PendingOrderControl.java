package com.dispatching.feima.view.PresenterControl;

import com.dispatching.feima.entity.OrderDeliveryResponse;
import com.dispatching.feima.entity.ShopResponse;

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
        void getShopSuccess(ShopResponse response);
    }

    public interface PresenterPendingOrder extends Presenter<PendingOrderView> {
        void requestShopId(String token, Integer uId);
        void requestTakeOrder(String token, String uId, String deliveryId);
        void requestUpOrder(String businessId);
    }
}
