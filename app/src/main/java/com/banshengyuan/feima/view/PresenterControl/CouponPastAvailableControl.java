package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.MyCoupleResponse;

/**
 * Created by lei.he on 2017/6/28.
 * AddAddressControl
 * 我的卡券---已过期
 */

public class CouponPastAvailableControl {
    public interface CouponPastAvailableView extends LoadDataView {
        void loadFail(Throwable throwable);
        void getExpiredCoupleListSuccess(MyCoupleResponse myCoupleResponse);
    }

    public interface PresenterCouponPastAvailable extends Presenter<CouponPastAvailableView> {
        void requestExpiredCouponList(String state, int page, int pageSize, String token);
    }
}
