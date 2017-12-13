package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.FairContentDetailResponse;

/**
 * Created by helei on 2017/5/3.
 * CompletedOrderControl
 */

public class WorkSummaryControl {

    public interface WorkSummaryView extends LoadDataView {
        void getFairDetailSuccess(FairContentDetailResponse response);
    }

    public interface PresenterWorkSummary extends Presenter<WorkSummaryView> {
        void requestFairDetail(Integer fairId);
    }
}
