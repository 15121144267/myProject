package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.MyCollectionResponse;

/**
 * Created by lei.he on 2017/6/28.
 * AddAddressControl
 */

public class CollectionHotControl {
    public interface CollectionHotView extends LoadDataView {
        void getMyCollectionListSuccess(MyCollectionResponse response);
    }

    public interface PresenterCollectionHot extends Presenter<CollectionHotView> {
        void requestCollectionHotList(int page,int pageSize);
    }
}
