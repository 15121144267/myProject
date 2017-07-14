package com.dispatching.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.BuildConfig;
import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.network.RetrofitUtil;
import com.dispatching.feima.network.networkapi.WelcomeApi;
import com.dispatching.feima.view.PresenterControl.WelcomeControl;
import com.dispatching.feima.view.PresenterImpl.PresenterWelcomeImpl;
import com.dispatching.feima.view.model.ModelTransform;
import com.dispatching.feima.view.model.WelcomeModel;
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
