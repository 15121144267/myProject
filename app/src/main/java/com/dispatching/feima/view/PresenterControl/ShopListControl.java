package com.dispatching.feima.view.PresenterControl;

import com.dispatching.feima.entity.ShopListResponse;

import java.util.List;

/**
 * Created by helei on 2017/4/27.
 * LoginControl
 */

public class ShopListControl {
    public interface ShopListView extends LoadDataView{
        void getShopListSuccess(List<ShopListResponse.ListBean> list);
        void loadFail(Throwable throwable);
    }

    public interface PresenterShopList extends Presenter<ShopListView> {
        void requestShopList(Integer pagerNo ,Integer pagerSize);
    }

}
