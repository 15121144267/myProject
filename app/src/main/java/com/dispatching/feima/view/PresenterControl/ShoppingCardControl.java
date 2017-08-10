package com.dispatching.feima.view.PresenterControl;

import android.widget.CheckBox;

import com.dispatching.feima.dagger.module.ShoppingCardListResponse;
import com.dispatching.feima.view.adapter.ShoppingCardItemAdapter;

/**
 * Created by lei.he on 2017/6/28.
 */

public class ShoppingCardControl {
    public interface ShoppingCardView extends LoadDataView {
        void shoppingCardListSuccess(ShoppingCardListResponse response);
        void setChildAdapter(Integer position, ShoppingCardItemAdapter itemAdapter, CheckBox checkBox);
    }

    public interface PresenterShoppingCard extends Presenter<ShoppingCardView> {
        void requestShoppingCardList(String companyId, String userId);
    }
}
