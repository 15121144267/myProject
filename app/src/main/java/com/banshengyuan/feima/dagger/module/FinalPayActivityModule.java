package com.banshengyuan.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.entity.BuProcessor;
import com.banshengyuan.feima.network.RetrofitUtil;
import com.banshengyuan.feima.network.networkapi.FinalPayApi;
import com.banshengyuan.feima.view.PresenterControl.FinalPayControl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterFinalPayImpl;
import com.banshengyuan.feima.view.model.FinalPayModel;
import com.banshengyuan.feima.view.model.ModelTransform;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityModule
 */
@Module
public class FinalPayActivityModule {
    private final AppCompatActivity activity;
    private final FinalPayControl.FinalPayView view;

    public FinalPayActivityModule(AppCompatActivity activity, FinalPayControl.FinalPayView view) {
        this.activity = activity;
        this.view = view;
    }

    @Provides
    @PerActivity
    AppCompatActivity activity() {
        return this.activity;
    }

    @Provides
    @PerActivity
    FinalPayControl.FinalPayView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    FinalPayModel provideFinalPayModel(Gson gson, ModelTransform modelTransform, BuProcessor buProcessor) {
        return new FinalPayModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.DISPATCH_SERVICE)
                .isHttps(!BuildConfig.DEBUG)
                .key(BuildConfig.STORE_NAME, BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(FinalPayApi.class), gson, modelTransform,buProcessor);
    }

    @Provides
    @PerActivity
    FinalPayControl.PresenterFinalPay providePresenterFinalPay(PresenterFinalPayImpl presenter) {
        return presenter;
    }
}
