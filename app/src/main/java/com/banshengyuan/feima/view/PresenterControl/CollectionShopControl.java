package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.MyCollectionResponse;

/**
 * Created by lei.he on 2017/6/28.
 * AddAddressControl
 */

public class CollectionShopControl {
    public interface CollectionShopView extends LoadDataView {
        void getMyCollectionListSuccess(MyCollectionResponse response);
    }

    public interface PresenterCollectionShop extends Presenter<CollectionShopView> {
        void requestCollectionShopList(int page,int pageSize);
    }
}
