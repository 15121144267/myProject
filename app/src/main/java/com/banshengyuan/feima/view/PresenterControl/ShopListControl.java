package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.ShopDetailBannerResponse;
import com.banshengyuan.feima.entity.ShopListResponse;

import java.util.List;

/**
 * Created by helei on 2017/4/27.
 * LoginControl
 */

public class ShopListControl {
    public interface ShopListView extends LoadDataView{
        void getShopListSuccess(List<ShopListResponse.ListBean> list);
        void loadFail(Throwable throwable);
        void getShopListBannerSuccess(ShopDetailBannerResponse response);
    }

    public interface PresenterShopList extends Presenter<ShopListView> {
        void requestShopList(Integer pagerNo ,Integer pagerSize);
        void requestShopListBanner(String partnerId);
    }

}
