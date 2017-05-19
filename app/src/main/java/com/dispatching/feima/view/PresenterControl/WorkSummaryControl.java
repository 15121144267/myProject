package com.dispatching.feima.view.PresenterControl;

import com.dispatching.feima.entity.OrderDeliveryResponse;

/**
 * Created by helei on 2017/5/3.
 * CompletedOrderControl
 */

public class WorkSummaryControl {

    public interface WorkSummaryView extends LoadDataView {
        void getAllOrderSuccess(OrderDeliveryResponse response);
    }

    public interface PresenterWorkSummary extends Presenter<WorkSummaryView> {
        void requestAllOrderInfo(String token, String uId, String startTime, String endTime);
    }
}
