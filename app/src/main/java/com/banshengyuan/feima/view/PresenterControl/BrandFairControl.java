package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.BrandAllFairListResponse;

/**
 * Created by lei.he on 2017/6/28.
 * AddAddressControl
 */

public class BrandFairControl {
    public interface  BrandFairView extends LoadDataView {
        void getFairListSuccess(BrandAllFairListResponse response);
        void getFairListFail();
    }

    public interface PresenterBrandFair extends Presenter<BrandFairView> {
        void requestFairList();
    }
}
