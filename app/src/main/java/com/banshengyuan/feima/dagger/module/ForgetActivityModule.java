package com.banshengyuan.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.network.RetrofitUtil;
import com.banshengyuan.feima.network.networkapi.ForgetApi;
import com.banshengyuan.feima.view.PresenterControl.ForgetControl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterForgetImpl;
import com.banshengyuan.feima.view.model.ForgetModel;
import com.banshengyuan.feima.view.model.ModelTransform;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityModule
 */
@Module
public class ForgetActivityModule {
    private final AppCompatActivity activity;
    private final ForgetControl.ForgetView view;

    public ForgetActivityModule(AppCompatActivity activity, ForgetControl.ForgetView view) {
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
    ForgetControl.ForgetView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    ForgetModel provideForgetModel(Gson gson, ModelTransform modelTransform) {
        return new ForgetModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.DISPATCH_SERVICE)
                .isHttps(!BuildConfig.DEBUG)
                .key(BuildConfig.STORE_NAME,BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(ForgetApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    ForgetControl.PresenterForget providePresenterForget(PresenterForgetImpl presenter) {
        return presenter;
    }
}
