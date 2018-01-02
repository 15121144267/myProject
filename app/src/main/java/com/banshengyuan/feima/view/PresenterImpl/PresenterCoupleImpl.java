package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.view.PresenterControl.CoupleControl;
import com.banshengyuan.feima.view.model.CoupleModel;

import javax.inject.Inject;

/**
 * Created by lei.he on 2017/6/26.
 * PresenterAddAddressImpl
 */

public class PresenterCoupleImpl implements CoupleControl.PresenterCouple {
    private CoupleControl.CoupleView mView;
    private Context mContext;
    private CoupleModel mModel;

    @Inject
    public PresenterCoupleImpl(Context context, CoupleControl.CoupleView view, CoupleModel model) {
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
