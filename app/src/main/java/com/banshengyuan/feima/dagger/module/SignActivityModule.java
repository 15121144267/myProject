package com.banshengyuan.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.network.RetrofitUtil;
import com.banshengyuan.feima.network.networkapi.SignApi;
import com.banshengyuan.feima.view.PresenterControl.SignControl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterSignImpl;
import com.banshengyuan.feima.view.model.ModelTransform;
import com.banshengyuan.feima.view.model.SignModel;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityModule
 */
@Module
public class SignActivityModule {
    private final AppCompatActivity activity;
    private final SignControl.SignView view;

    public SignActivityModule(AppCompatActivity activity, SignControl.SignView view) {
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
    SignControl.SignView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    SignModel provideSignModel(Gson gson, ModelTransform modelTransform) {
        return new SignModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.DISPATCH_SERVICE)
                .isHttps(!BuildConfig.DEBUG)
                .key(BuildConfig.STORE_NAME,BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(SignApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    SignControl.PresenterSign providePresenterSign(PresenterSignImpl presenter) {
        return presenter;
    }
}
