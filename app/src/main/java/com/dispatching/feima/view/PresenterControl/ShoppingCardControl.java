package com.dispatching.feima.view.PresenterControl;

import com.dispatching.feima.dagger.module.ShoppingCardListResponse;

/**
 * Created by lei.he on 2017/6/28.
 */

public class ShoppingCardControl {
    public interface ShoppingCardView extends LoadDataView {
        void shoppingCardListSuccess(ShoppingCardListResponse response);
    }

    public interface PresenterShoppingCard extends Presenter<ShoppingCardView> {
        void requestShoppingCardList(String companyId, String userId);
    }
}
