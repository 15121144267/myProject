package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.ProductCategoryResponse;

/**
 * Created by lei.he on 2017/6/28.
 * AddAddressControl
 */

public class ProductListControl {
    public interface ProductListView extends LoadDataView {
        void getProductListSuccess(ProductCategoryResponse response);
        void getProductListFail(String des);
    }

    public interface PresenterProductList extends Presenter<ProductListView> {
        void requestProductList(Integer categoryId);
    }
}
