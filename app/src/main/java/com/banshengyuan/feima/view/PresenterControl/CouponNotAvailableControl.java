package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.MyCoupleResponse;

/**
 * Created by lei.he on 2017/6/28.
 * AddAddressControl
 */

public class CouponNotAvailableControl {
    public interface CouponNotAvailableView extends LoadDataView {
        void getUsedCoupleListSuccess(MyCoupleResponse myCoupleResponse);
    }

    public interface PresenterCouponNotAvailable extends Presenter<CouponNotAvailableView> {
        void requestUsedCouponList(String state, int page, int pageSize, String token);
    }
}
