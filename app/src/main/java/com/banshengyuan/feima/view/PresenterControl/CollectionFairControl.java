package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.MyCollectionResponse;

/**
 * Created by lei.he on 2017/6/28.
 * AddAddressControl
 */

public class CollectionFairControl {
    public interface CollectionFairView extends LoadDataView {
        void getMyCollectionListSuccess(MyCollectionResponse response);
    }

    public interface PresenterCollectionFair extends Presenter<CollectionFairView> {
        void requestCollectionFairList(int page,int pageSize);
    }
}
