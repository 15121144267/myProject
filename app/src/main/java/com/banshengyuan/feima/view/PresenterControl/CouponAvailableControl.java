package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.MyCoupleResponse;

/**
 * Created by lei.he on 2017/6/28.
 * AddAddressControl
 */

public class CouponAvailableControl {
    public interface CouponAvailableView extends LoadDataView {
        void getNoUseCouponListSuccess(MyCoupleResponse myCoupleResponse);
    }

    public interface PresenterCouponAvailable extends Presenter<CouponAvailableView> {
        void requestNoUseCouponList(String state,int page,int pageSize,String token);
    }
}
