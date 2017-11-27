package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.MyCollectionResponse;

/**
 * Created by lei.he on 2017/6/28.
 * AddAddressControl
 */

public class CollectionProductControl {
    public interface CollectionProductView extends LoadDataView {
        void getMyCollectionListSuccess(MyCollectionResponse response);
    }

    public interface PresenterCollectionProduct extends Presenter<CollectionProductView> {
        void requestCollectionProductList(int page,int pageSize);
    }
}
