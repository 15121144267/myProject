package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.MyCollectionBlockResponse;

/**
 * Created by lei.he on 2017/6/28.
 * AddAddressControl
 */

public class CollectionBlockControl {
    public interface CollectionBlockView extends LoadDataView {
        void getMyCollectionListSuccess(MyCollectionBlockResponse response);
    }

    public interface PresenterCollectionBlock extends Presenter<CollectionBlockView> {
        void requestCollectionBlockList(int page,int pageSize,String token);
    }
}
