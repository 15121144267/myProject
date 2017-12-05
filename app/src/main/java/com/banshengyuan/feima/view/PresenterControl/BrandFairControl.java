package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.RecommendBrandResponse;

/**
 * Created by lei.he on 2017/6/28.
 * AddAddressControl
 */

public class BrandFairControl {
    public interface  BrandFairView extends LoadDataView {
        void getFairListSuccess(RecommendBrandResponse response);
        void getFairListFail();
    }

    public interface PresenterBrandFair extends Presenter<BrandFairView> {
        void requestFairList();
    }
}
