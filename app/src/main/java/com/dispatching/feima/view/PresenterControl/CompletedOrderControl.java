package com.dispatching.feima.view.PresenterControl;

import com.dispatching.feima.entity.OrderDeliveryResponse;

/**
 * Created by helei on 2017/5/3.
 */

public class CompletedOrderControl {
    public interface CompletedOrderView extends LoadDataView{
        void getCompletedOrderSuccess(OrderDeliveryResponse response);
    }

    public interface PresenterCompletedOrder extends Presenter<CompletedOrderView> {
        void requestCompletedOrder(Integer position,String token,String version,String uId);
    }
}
