package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.ShopSortListResponse;
import com.banshengyuan.feima.entity.StoreCategoryListResponse;
import com.banshengyuan.feima.entity.StreetSortListResponse;

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
        void getStreetSortListSuccess(StreetSortListResponse response);
        void getStreetSortListFail(String des);
    }

    public interface PresenterShopBlock extends Presenter<ShopBlockView> {
        void requestShopSortList();
        void requestStreetSortList();
        void requestShopList(Integer streetId,Integer categoryId);
    }
}
