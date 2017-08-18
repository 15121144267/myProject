package com.dispatching.feima.view.PresenterControl;

import com.dispatching.feima.entity.ShopDetailResponse;

/**
 * Created by helei on 2017/4/27.
 * LoginControl
 */

public class SearchControl {
    public interface SearchView extends LoadDataView {
        void getProductListSuccess(ShopDetailResponse response);
    }

    public interface PresenterSearch extends Presenter<SearchView> {
        void requestProductList(String searchName, String partnerId, String sortName, Integer sortNO, Integer pagerSize, Integer pagerNo);
    }

}
