package com.banshengyuan.feima.view.PresenterControl;

import android.widget.CheckBox;

import com.banshengyuan.feima.dagger.module.ShoppingCardListResponse;
import com.banshengyuan.feima.view.adapter.ShoppingCardItemAdapter;

/**
 * Created by lei.he on 2017/6/28.
 * ShoppingCardControl
 */

public class ShoppingCardControl {
    public interface ShoppingCardView extends LoadDataView {
        void shoppingCardListSuccess(ShoppingCardListResponse response);
        void shoppingCardListFail(String des);
        void deleteProductSuccess();
        void changeProductNumberSuccess();
        void changeProductNumberFail(String des);
//        void deleteProduct(ShoppingCardListResponse.DataBean product, ShoppingCardListResponse.DataBean.ProductsBean childProduct,Integer position);
        void setChildAdapter(Integer position, ShoppingCardItemAdapter itemAdapter, CheckBox checkBox);
    }

    public interface PresenterShoppingCard extends Presenter<ShoppingCardView> {
        void requestShoppingCardList();
        void requestDeleteProduct(Integer productId);
        void requestChangeProductNumber(Integer productId,String sku,Integer number);
    }
}
