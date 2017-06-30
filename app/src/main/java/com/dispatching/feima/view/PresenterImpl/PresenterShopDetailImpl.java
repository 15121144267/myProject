package com.dispatching.feima.view.PresenterImpl;

import android.content.Context;

import com.dispatching.feima.view.PresenterControl.ShopDetailControl;
import com.dispatching.feima.view.model.ShopDetailModel;

import javax.inject.Inject;

/**
 * Created by lei.he on 2017/6/26.
 */

public class PresenterShopDetailImpl implements ShopDetailControl.PresenterShopDetail {

    private ShopDetailControl.ShopDetailView mView;

    @Inject
    public PresenterShopDetailImpl(Context context, ShopDetailControl.ShopDetailView view, ShopDetailModel model) {
        mView = view;
    }


    @Override
    public void onCreate() {

    }


    @Override
    public void onDestroy() {
        mView = null;
    }
}
