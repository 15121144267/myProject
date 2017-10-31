package com.banshengyuan.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.network.RetrofitUtil;
import com.banshengyuan.feima.network.networkapi.CoupleApi;
import com.banshengyuan.feima.view.PresenterControl.CoupleControl;
import com.banshengyuan.feima.view.PresenterControl.CouponAvailableControl;
import com.banshengyuan.feima.view.PresenterControl.CouponNotAvailableControl;
import com.banshengyuan.feima.view.PresenterControl.CouponPastAvailableControl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterCoupleImpl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterCouponAvailableImpl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterCouponNotAvailableImpl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterCouponPastImpl;
import com.banshengyuan.feima.view.model.CoupleModel;
import com.banshengyuan.feima.view.model.ModelTransform;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityModule
 */
@Module
public class CoupleActivityModule {
    private final AppCompatActivity activity;
    private CoupleControl.CoupleView view;

    public CoupleActivityModule(AppCompatActivity activity, CoupleControl.CoupleView view) {
        this.activity = activity;
        this.view = view;
    }

    public CoupleActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    AppCompatActivity activity() {
        return this.activity;
    }

    @Provides
    @PerActivity
    CoupleControl.CoupleView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    CoupleModel provideCoupleModel(Gson gson, ModelTransform modelTransform) {
        return new CoupleModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.DISPATCH_SERVICE)
                .isHttps(!BuildConfig.DEBUG)
                .key(BuildConfig.STORE_NAME, BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(CoupleApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    CoupleControl.PresenterCouple providePresenterCouple(PresenterCoupleImpl presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    CouponAvailableControl.PresenterCouponAvailable providePresenterCouponAvailable(PresenterCouponAvailableImpl presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    CouponNotAvailableControl.PresenterCouponNotAvailable providePresenterCouponNotAvailable(PresenterCouponNotAvailableImpl presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    CouponPastAvailableControl.PresenterCouponPastAvailable providePresenterCouponPastAvailable(PresenterCouponPastImpl presenter) {
        return presenter;
    }
}
