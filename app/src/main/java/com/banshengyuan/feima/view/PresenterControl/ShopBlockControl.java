package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.ShopSortListResponse;
import com.banshengyuan.feima.entity.StoreCategoryListResponse;

/**
 * Created by lei.he on 2017/6/28.
 * AddAddressControl
 */

public class ShopBlockControl {
    public interface ShopBlockView extends LoadDataView {
        void getShopSortListSuccess(ShopSortListResponse response);
        void getShopSortListFail(String des);
        void getShopListSuccess(StoreCategoryListResponse response);
        void getShopListFail(String des);
    }

    public interface PresenterShopBlock extends Presenter<ShopBlockView> {
        void requestShopSortList();
        void requestShopList(Integer streetId,Integer categoryId);
    }
}
