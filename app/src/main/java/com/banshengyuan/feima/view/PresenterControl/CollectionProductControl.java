package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.MyCollectionProductsResponse;

/**
 * Created by lei.he on 2017/6/28.
 * AddAddressControl
 */

public class CollectionProductControl {
    public interface CollectionProductView extends LoadDataView {
        void loadFail(Throwable throwable);
        void getMyCollectionListSuccess(MyCollectionProductsResponse response);
    }

    public interface PresenterCollectionProduct extends Presenter<CollectionProductView> {
        void requestCollectionProductList(int page,int pageSize,String token);
    }
}
