package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.ShopSortListResponse;

/**
 * Created by lei.he on 2017/6/28.
 * AddAddressControl
 */

public class ShopBlockControl {
    public interface ShopBlockView extends LoadDataView {
        void getShopSortListSuccess(ShopSortListResponse response);
        void getShopSortListFail(String des);
    }

    public interface PresenterShopBlock extends Presenter<ShopBlockView> {
        void requestShopSortList();
    }
}
