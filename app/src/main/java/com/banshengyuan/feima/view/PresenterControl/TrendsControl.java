package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.FairDetailProductListResponse;

/**
 * Created by helei on 2017/5/3.
 * PendingOrderControl
 */

public class TrendsControl {
    public interface TrendsView extends LoadDataView{
        void getProductListSuccess(FairDetailProductListResponse response);
        void getProductListFail();
    }

    public interface PresenterTrends extends Presenter<TrendsView> {
        void requestProductList(Integer fairId);
    }
}
