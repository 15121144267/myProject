package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.AllProductSortResponse;

/**
 * Created by helei on 2017/5/3.
 * PendingOrderControl
 */

public class ProductControl {
    public interface ProductView extends LoadDataView {
        void getAllProductSortSuccess(AllProductSortResponse allProductSortResponse);

        void getAllProductSortFail();
    }

    public interface PresenterProduct extends Presenter<ProductView> {
        void requestAllProductSort();
    }
}
