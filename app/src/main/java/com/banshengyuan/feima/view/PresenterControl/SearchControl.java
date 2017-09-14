package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.SearchShopListResponse;
import com.banshengyuan.feima.entity.ShopDetailResponse;

/**
 * Created by helei on 2017/4/27.
 * LoginControl
 */

public class SearchControl {
    public interface SearchView extends LoadDataView {
        void getProductListSuccess(ShopDetailResponse response);
        void getShopListSuccess(SearchShopListResponse response);
    }

    public interface PresenterSearch extends Presenter<SearchView> {
        void requestProductList(String searchName, String partnerId, String sortName, Integer sortNO, Integer pagerSize, Integer pagerNo);

        void requestShopList(String partnerId, String searchName);
    }

}
