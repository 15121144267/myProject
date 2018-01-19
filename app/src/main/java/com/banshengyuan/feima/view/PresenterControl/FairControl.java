package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.FairListResponse;
import com.banshengyuan.feima.entity.RecommendBrandResponse;

/**
 * Created by helei on 2017/5/3.
 * PendingOrderControl
 */

public class FairControl {
    public interface FairView extends LoadDataView{
        void getFairBrandSuccess(RecommendBrandResponse response);
        void getFairBrandFail();
        void getFairListSuccess(FairListResponse response);
        void getFairListFail();
        void getFairBrandComplete();
        void getFairListComplete();
    }

    public interface PresenterFair extends Presenter<FairView> {
        void requestFairBrand();
        void requestFairList();
    }
}
