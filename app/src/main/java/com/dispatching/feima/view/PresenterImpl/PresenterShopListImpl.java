package com.dispatching.feima.view.PresenterImpl;

import android.content.Context;

import com.dispatching.feima.view.PresenterControl.ShopListControl;
import com.dispatching.feima.view.model.ShopListModel;

import javax.inject.Inject;

/**
 * Created by lei.he on 2017/6/26.
 */

public class PresenterShopListImpl implements ShopListControl.PresenterShopList {
    private Context mContext;
    private ShopListControl.ShopListView mView;
    private ShopListModel mModel;

    @Inject
    public PresenterShopListImpl(Context context,ShopListControl.ShopListView view, ShopListModel model) {
        mContext = context;
        mView = view;
        mModel = model;
    }


    @Override
    public void onCreate() {

    }


    @Override
    public void onDestroy() {
        mView = null;
    }
}
