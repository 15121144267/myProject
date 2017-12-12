package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.PersonInfoResponse;

/**
 * Created by helei on 2017/5/3.
 * CompletedOrderControl
 */

public class CompletedOrderControl {
    public interface CompletedOrderView extends LoadDataView {

        void getPersonInfoSuccess(PersonInfoResponse response);

    }

    public interface PresenterCompletedOrder extends Presenter<CompletedOrderView> {
        void requestPersonInfo(String token);
    }
}
