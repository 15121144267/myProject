package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.FairBottomResponse;
import com.banshengyuan.feima.entity.FairUnderLineResponse;
import com.banshengyuan.feima.entity.RecommendBrandResponse;

/**
 * Created by helei on 2017/5/3.
 * PendingOrderControl
 */

public class MainFairControl {
    public interface MainFairView extends LoadDataView {
        void getRecommendBrandSuccess(RecommendBrandResponse recommendBrandResponse);

        void getRecommendBrandFail();

        void getFairUnderLineSuccess(FairUnderLineResponse fairUnderLineResponse);

        void getFairUnderLineFail();

        void getFairBottomSuccess(FairBottomResponse fairBottomResponse);

        void getFairBottomFail();
    }

    public interface PresenterFair extends Presenter<MainFairView> {
        void requestRecommendBrand();

        void requestFairUnderLine();

        void requestFairBottom();
    }
}
