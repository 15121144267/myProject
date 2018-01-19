package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.AllProductSortResponse;
import com.banshengyuan.feima.entity.ProductListResponse;
import com.example.mylibrary.adapter.BaseQuickAdapter;

/**
 * Created by helei on 2017/5/3.
 * PendingOrderControl
 */

public class ProductControl {
    public interface ProductView extends LoadDataView {
        void getAllProductSortSuccess(AllProductSortResponse allProductSortResponse);

        void getAllProductSortFail();

        void getProductListSuccess(ProductListResponse response);

        void getProductListFail();

        void getAllProductSortComplete();

        void getProductListComplete();

        void switchToProductList(BaseQuickAdapter adapter, Integer position);
    }

    public interface PresenterProduct extends Presenter<ProductView> {
        void requestAllProductSort();

        void requestProductList();
    }
}
