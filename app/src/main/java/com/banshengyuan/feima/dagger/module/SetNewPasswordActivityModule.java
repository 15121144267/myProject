package com.banshengyuan.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.network.RetrofitUtil;
import com.banshengyuan.feima.network.networkapi.NewPasswordApi;
import com.banshengyuan.feima.view.PresenterControl.SetNewPasswordControl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterSetNewPasswordImpl;
import com.banshengyuan.feima.view.model.ModelTransform;
import com.banshengyuan.feima.view.model.NewPasswordModel;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityModule
 */
@Module
public class SetNewPasswordActivityModule {
    private final AppCompatActivity activity;
    private final SetNewPasswordControl.SetNewPasswordView view;

    public SetNewPasswordActivityModule(AppCompatActivity activity,  SetNewPasswordControl.SetNewPasswordView view) {
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
    SetNewPasswordControl.SetNewPasswordView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    NewPasswordModel provideForgetModel(Gson gson, ModelTransform modelTransform) {
        return new NewPasswordModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.DISPATCH_SERVICE)
                .isHttps(!BuildConfig.DEBUG)
                .key(BuildConfig.STORE_NAME,BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(NewPasswordApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    SetNewPasswordControl.PresenterSetNewPassword providePresenterSetNewPassword(PresenterSetNewPasswordImpl presenter) {
        return presenter;
    }
}
