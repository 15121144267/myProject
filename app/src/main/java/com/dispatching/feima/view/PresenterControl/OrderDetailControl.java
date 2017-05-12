package com.dispatching.feima.view.PresenterControl;

import com.dispatching.feima.entity.DeliveryStatusResponse;

/**
 * Created by helei on 2017/4/27.
 * OrderDetailControl
 */

public class OrderDetailControl {
    public interface OrderDetailView extends LoadDataView {
        void updateOrderStatusSuccess(DeliveryStatusResponse response);
    }

    public interface PresenterOrderDetail extends Presenter<OrderDetailView> {
        void requestUpdateOrder(Integer position, String token, String version, String uId, String delivery);
    }

}
