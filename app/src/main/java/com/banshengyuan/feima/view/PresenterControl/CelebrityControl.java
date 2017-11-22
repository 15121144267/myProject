package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.FairDetailStoreListResponse;

/**
 * Created by helei on 2017/5/3.
 * PendingOrderControl
 */

public class CelebrityControl {
    public interface CelebrityView extends LoadDataView{
        void getStoreListSuccess(FairDetailStoreListResponse response);
        void getStoreListFail();
    }

    public interface PresenterCelebrity extends Presenter<CelebrityView> {
        void requestStoreList(Integer fairId);
    }
}
