package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.FairDetailListResponse;

/**
 * Created by helei on 2017/5/3.
 * PendingOrderControl
 */

public class FollowControl {
    public interface FollowView extends LoadDataView{
        void getFairListSuccess(FairDetailListResponse response);
        void getFairListFail();
    }

    public interface PresenterFollow extends Presenter<FollowView> {
        void requestFairList(Integer fairId);
    }
}
