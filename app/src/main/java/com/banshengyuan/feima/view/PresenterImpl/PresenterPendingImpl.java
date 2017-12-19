package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.view.PresenterControl.PendingOrderControl;
import com.banshengyuan.feima.view.model.MainModel;
import com.banshengyuan.feima.view.model.ResponseData;
import com.banshengyuan.feima.view.model.ShopListModel;

import javax.inject.Inject;

/**
 * Created by helei on 2017/5/3.
 * PresenterPendingImpl
 */

public class PresenterPendingImpl implements PendingOrderControl.PresenterPendingOrder {
    private final MainModel mMainModel;
    private PendingOrderControl.PendingOrderView mView;
    private final Context mContext;
    private ShopListModel mShopListModel;

    @Inject
    public PresenterPendingImpl(Context context, MainModel model, PendingOrderControl.PendingOrderView view, ShopListModel shopListModel) {
        mContext = context;
        mMainModel = model;
        mView = view;
        mShopListModel = shopListModel;
    }

    @Override
    public void requestShopId(String scoreCode, Integer type) {

    }

    private void getShopSuccess(ResponseData responseData) {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mView = null;
    }
}
