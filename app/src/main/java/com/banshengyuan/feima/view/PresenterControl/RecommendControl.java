package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.RecommendBottomResponse;
import com.banshengyuan.feima.entity.RecommendBrandResponse;
import com.banshengyuan.feima.entity.RecommendTopResponse;

/**
 * Created by helei on 2017/5/3.
 * PendingOrderControl
 */

public class RecommendControl {
    public interface RecommendView extends LoadDataView {
        void getRecommendTopSuccess(RecommendTopResponse recommendTopResponse);
        void getRecommendBrandSuccess(RecommendBrandResponse recommendBrandResponse);
        void getRecommendBrandFail();
        void getRecommendBottomSuccess(RecommendBottomResponse recommendBottomResponse);
        void getRecommendBottomFail();
        void requestRecommendBottomComplete();
        void requestRecommendBrandComplete();
        void requestRecommendTopComplete();
    }

    public interface PresenterRecommend extends Presenter<RecommendView> {
        void requestRecommendTop(double longitude, double latitude);
        void requestRecommendBrand();
        void requestRecommendBottom();
    }
}
