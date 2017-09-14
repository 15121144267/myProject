package com.banshengyuan.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.network.RetrofitUtil;
import com.banshengyuan.feima.network.networkapi.LoginApi;
import com.banshengyuan.feima.view.PresenterControl.LoginControl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterLoginImpl;
import com.banshengyuan.feima.view.model.LoginModel;
import com.banshengyuan.feima.view.model.ModelTransform;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityModule
 */
@Module
public class LoginActivityModule {
    private final AppCompatActivity activity;
    private final LoginControl.LoginView view;

    public LoginActivityModule(AppCompatActivity activity, LoginControl.LoginView view) {
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
    LoginControl.LoginView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    LoginModel provideLoginModel(Gson gson, ModelTransform modelTransform ) {
        return new LoginModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.DISPATCH_SERVICE)
                .isHttps(!BuildConfig.DEBUG)
                .key(BuildConfig.STORE_NAME,BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(LoginApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    LoginControl.PresenterLogin providePresenterLogin(PresenterLoginImpl presenterLogin) {
        return presenterLogin;
    }
}
