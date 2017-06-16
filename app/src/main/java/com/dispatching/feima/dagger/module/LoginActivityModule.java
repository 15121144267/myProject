package com.dispatching.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.BuildConfig;
import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.network.RetrofitUtil;
import com.dispatching.feima.network.networkapi.LoginApi;
import com.dispatching.feima.superscoket.SocketClient;
import com.dispatching.feima.view.PresenterControl.LoginControl;
import com.dispatching.feima.view.PresenterImpl.PresenterLoginImpl;
import com.dispatching.feima.view.model.LoginModel;
import com.dispatching.feima.view.model.ModelTransform;
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
    LoginModel provideLoginModel(Gson gson, ModelTransform modelTransform, SocketClient client) {
        return new LoginModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.DISPATCH_SERVICE)
                .isToJson(false)
                .builder()
                .create(LoginApi.class), gson, modelTransform, client);
    }

    @Provides
    @PerActivity
    LoginControl.PresenterLogin providePresenterLogin(PresenterLoginImpl presenterLogin) {
        return presenterLogin;
    }
}
