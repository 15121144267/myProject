package com.banshengyuan.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.network.RetrofitUtil;
import com.banshengyuan.feima.network.networkapi.WelcomeApi;
import com.banshengyuan.feima.view.PresenterControl.WelcomeControl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterWelcomeImpl;
import com.banshengyuan.feima.view.model.ModelTransform;
import com.banshengyuan.feima.view.model.WelcomeModel;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityModule
 */
@Module
public class WelcomeActivityModule {
    private final AppCompatActivity activity;
    private final WelcomeControl.WelcomeView view;

    public WelcomeActivityModule(AppCompatActivity activity, WelcomeControl.WelcomeView view) {
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
    WelcomeControl.WelcomeView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    WelcomeModel provideWelcomeModel(Gson gson, ModelTransform modelTransform ) {
        return new WelcomeModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.DISPATCH_SERVICE)
                .isHttps(!BuildConfig.DEBUG)
                .key(BuildConfig.STORE_NAME,BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(WelcomeApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    WelcomeControl.PresenterWelcome providePresenterWelcome(PresenterWelcomeImpl presenter) {
        return presenter;
    }
}
