package com.dispatching.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.BuildConfig;
import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.network.RetrofitUtil;
import com.dispatching.feima.network.networkapi.NewPasswordApi;
import com.dispatching.feima.view.PresenterControl.SetNewPasswordControl;
import com.dispatching.feima.view.PresenterImpl.PresenterSetNewPasswordImpl;
import com.dispatching.feima.view.model.ModelTransform;
import com.dispatching.feima.view.model.NewPasswordModel;
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
