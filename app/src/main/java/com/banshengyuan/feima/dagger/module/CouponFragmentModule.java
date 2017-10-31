package com.banshengyuan.feima.dagger.module;


import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.view.PresenterControl.CouponAvailableControl;
import com.banshengyuan.feima.view.PresenterControl.CouponNotAvailableControl;
import com.banshengyuan.feima.view.PresenterControl.CouponPastAvailableControl;
import com.banshengyuan.feima.view.PresenterControl.LoadDataView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/5/3.
 * FragmentModule
 */
@Module
public class CouponFragmentModule {
    private final AppCompatActivity activity;

    private CouponAvailableControl.CouponAvailableView mCouponAvailableView;
    private CouponNotAvailableControl.CouponNotAvailableView mCouponNotAvailableView;
    private CouponPastAvailableControl.CouponPastAvailableView mCouplePastAvailableView;

    public CouponFragmentModule(LoadDataView view, AppCompatActivity activity) {
        this.activity = activity;
        if (view instanceof CouponAvailableControl.CouponAvailableView) {
            mCouponAvailableView = (CouponAvailableControl.CouponAvailableView) view;
        } else if (view instanceof CouponNotAvailableControl.CouponNotAvailableView) {
            mCouponNotAvailableView = (CouponNotAvailableControl.CouponNotAvailableView) view;
        } else if (view instanceof CouponPastAvailableControl.CouponPastAvailableView) {
            mCouplePastAvailableView = (CouponPastAvailableControl.CouponPastAvailableView) view;
        }
    }


    @Provides
    @PerActivity
    CouponAvailableControl.CouponAvailableView couponAvailableView() {
        return this.mCouponAvailableView;
    }

    @Provides
    @PerActivity
    CouponNotAvailableControl.CouponNotAvailableView couponNotAvailableView() {
        return this.mCouponNotAvailableView;
    }

    @Provides
    @PerActivity
    CouponPastAvailableControl.CouponPastAvailableView couponPastAvailableView() {
        return this.mCouplePastAvailableView;
    }

}
