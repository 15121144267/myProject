package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.MyCollectionFairResponse;

/**
 * Created by lei.he on 2017/6/28.
 * AddAddressControl
 */

public class CollectionFairControl {
    public interface CollectionFairView extends LoadDataView {
        void loadFail(Throwable throwable);
        void getMyCollectionListSuccess(MyCollectionFairResponse response);
    }

    public interface PresenterCollectionFair extends Presenter<CollectionFairView> {
        void requestCollectionFairList(int page,int pageSize,String token);
    }
}
