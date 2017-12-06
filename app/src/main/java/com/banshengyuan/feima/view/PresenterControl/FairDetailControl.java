package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.FairCategoryResponse;

/**
 * Created by lei.he on 2017/6/28.
 * AddAddressControl
 */

public class FairDetailControl {
    public interface FairDetailView extends LoadDataView {
        void getCategoryInfoSuccess(FairCategoryResponse response);
    }

    public interface PresenterFairDetail extends Presenter<FairDetailView> {
        void requestCategoryInfo(Integer fairCategoryId);
    }
}
