package com.banshengyuan.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.entity.BuProcessor;
import com.banshengyuan.feima.network.RetrofitUtil;
import com.banshengyuan.feima.network.networkapi.ReductionPayApi;
import com.banshengyuan.feima.view.PresenterControl.ReductionPayControl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterReductionPayImpl;
import com.banshengyuan.feima.view.model.ModelTransform;
import com.banshengyuan.feima.view.model.ReductionPayModel;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityModule
 */
@Module
public class ReductionPayActivityModule {
    private final AppCompatActivity activity;
    private final ReductionPayControl.ReductionPayView view;

    public ReductionPayActivityModule(AppCompatActivity activity, ReductionPayControl.ReductionPayView view) {
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
    ReductionPayControl.ReductionPayView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    ReductionPayModel provideReductionPayModel(Gson gson, ModelTransform modelTransform, BuProcessor buProcessor) {
        return new ReductionPayModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.DISPATCH_SERVICE)
                .isHttps(!BuildConfig.DEBUG)
                .key(BuildConfig.STORE_NAME, BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(ReductionPayApi.class), gson, modelTransform,buProcessor);
    }

    @Provides
    @PerActivity
    ReductionPayControl.PresenterReductionPay providePresenterReductionPay(PresenterReductionPayImpl presenter) {
        return presenter;
    }
}
