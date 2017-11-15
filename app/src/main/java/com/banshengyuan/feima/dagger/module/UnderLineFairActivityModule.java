package com.banshengyuan.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.network.RetrofitUtil;
import com.banshengyuan.feima.network.networkapi.UnderLineFairApi;
import com.banshengyuan.feima.view.PresenterControl.UnderLineFairControl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterUnderLineFairImpl;
import com.banshengyuan.feima.view.model.ModelTransform;
import com.banshengyuan.feima.view.model.UnderLineFairModel;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityModule
 */
@Module
public class UnderLineFairActivityModule {
    private final AppCompatActivity activity;
    private final UnderLineFairControl.UnderLineFairView view;

    public UnderLineFairActivityModule(AppCompatActivity activity, UnderLineFairControl.UnderLineFairView view) {
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
    UnderLineFairControl.UnderLineFairView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    UnderLineFairModel provideUnderLineFairModel(Gson gson, ModelTransform modelTransform) {
        return new UnderLineFairModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.DISPATCH_SERVICE)
                .isHttps(!BuildConfig.DEBUG)
                .key(BuildConfig.STORE_NAME, BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(UnderLineFairApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    UnderLineFairControl.PresenterUnderLineFair providePresenterUnderLineFairImpl(PresenterUnderLineFairImpl presenter) {
        return presenter;
    }
}
