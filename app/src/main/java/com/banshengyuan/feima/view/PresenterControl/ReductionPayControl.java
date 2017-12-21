package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.MyCoupleResponse;
import com.banshengyuan.feima.entity.OrderConfirmedResponse;
import com.banshengyuan.feima.entity.PayResponse;

/**
 * Created by lei.he on 2017/6/28.
 * AddAddressControl
 */

public class ReductionPayControl {
    public interface ReductionPayView extends LoadDataView {
        void getCouponListRequestSuccess(MyCoupleResponse response);

        void getPayRequestSuccess(OrderConfirmedResponse response);

        void orderPaySuccess();

        void orderPayInfoSuccess(PayResponse response);
    }

    public interface PresenterReductionPay extends Presenter<ReductionPayView> {
        void requestCouponList(String storeId, String status);

        void requestPay(String storeId, String amount, String discount, String payed);

        void requestPayInfo(OrderConfirmedResponse response, Integer payType, Integer channel);
    }
}
