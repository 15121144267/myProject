package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.OrderDeliveryResponse;

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
