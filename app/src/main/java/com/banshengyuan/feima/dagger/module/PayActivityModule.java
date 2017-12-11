package com.banshengyuan.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.entity.BuProcessor;
import com.banshengyuan.feima.network.RetrofitUtil;
import com.banshengyuan.feima.network.networkapi.PayApi;
import com.banshengyuan.feima.view.PresenterControl.PayControl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterPayImpl;
import com.banshengyuan.feima.view.model.ModelTransform;
import com.banshengyuan.feima.view.model.PayModel;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityModule
 */
@Module
public class PayActivityModule {
    private final AppCompatActivity activity;
    private final PayControl.PayView view;

    public PayActivityModule(AppCompatActivity activity, PayControl.PayView view) {
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
    PayControl.PayView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    PayModel providePayModel(Gson gson, ModelTransform modelTransform, BuProcessor buProcessor) {
        return new PayModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.DISPATCH_SERVICE)
                .isToJson(false)
                .builder()
                .create(PayApi.class), gson, modelTransform,buProcessor,activity);
    }

    @Provides
    @PerActivity
    PayControl.PresenterPay providePresenterPay(PresenterPayImpl presenter) {
        return presenter;
    }
}
