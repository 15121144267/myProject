package com.dispatching.feima.view.PresenterControl;

/**
 * Created by helei on 2017/4/27.
 * OrderDetailControl
 */

public class OrderDetailControl {
    public interface OrderDetailView extends LoadDataView {
        void updateOrderStatusSuccess();
    }

    public interface PresenterOrderDetail extends Presenter<OrderDetailView> {
        void requestUpdateOrder(Integer position, String token, String uId, String delivery);
    }

}
