package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.FairUnderLineResponse;
import com.banshengyuan.feima.entity.StoreListResponse;

/**
 * Created by helei on 2017/5/3.
 * PendingOrderControl
 */

public class SellerControl {
    public interface SellerView extends LoadDataView{
        void getBlockListSuccess(FairUnderLineResponse response);
        void getStoreListSuccess(StoreListResponse response);
        void getBlockListFail();
        void getStoreListFail();
    }

    public interface PresenterSeller extends Presenter<SellerView> {
        void requestBlockList(double longitude, double latitude);
        void requestStoreList();
    }
}
