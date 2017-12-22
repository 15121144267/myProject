package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.PayResponse;

/**
 * Created by lei.he on 2017/6/28.
 * AddAddressControl
 */

public class FinalPayControl {
    public interface FinalPayView extends LoadDataView {
        void orderPayInfoSuccess(PayResponse response);

        void orderPaySuccess();
    }

    public interface PresenterFinalPay extends Presenter<FinalPayView> {
        void requestPayInfo(String orderId, Integer payType,Integer orderType);

    }
}
