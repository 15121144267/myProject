package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.view.PresenterControl.HotControl;
import com.banshengyuan.feima.view.model.MainModel;

import javax.inject.Inject;

/**
 * Created by helei on 2017/5/3.
 * PresenterPendingImpl
 */

public class PresenterHotImpl implements HotControl.PresenterHot {
    private final MainModel mMainModel;
    private HotControl.HotView mView;
    private final Context mContext;

    @Inject
    public PresenterHotImpl(Context context, MainModel model, HotControl.HotView view) {
        mContext = context;
        mMainModel = model;
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
