package com.dispatching.feima.view.PresenterControl;

import com.dispatching.feima.entity.PersonInfoResponse;

/**
 * Created by helei on 2017/5/3.
 * CompletedOrderControl
 */

public class CompletedOrderControl {
    public interface CompletedOrderView extends LoadDataView {

        void getPersonInfoSuccess(PersonInfoResponse response);

    }

    public interface PresenterCompletedOrder extends Presenter<CompletedOrderView> {
        void requestPersonInfo(String phone);
    }
}
