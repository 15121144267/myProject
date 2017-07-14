package com.dispatching.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.BuildConfig;
import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.network.RetrofitUtil;
import com.dispatching.feima.network.networkapi.ForgetApi;
import com.dispatching.feima.view.PresenterControl.ForgetControl;
import com.dispatching.feima.view.PresenterImpl.PresenterForgetImpl;
import com.dispatching.feima.view.model.ForgetModel;
import com.dispatching.feima.view.model.ModelTransform;
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
