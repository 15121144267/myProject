package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.LoginActivityModule;
import com.banshengyuan.feima.view.PresenterControl.LoginControl;
import com.banshengyuan.feima.view.activity.LoginActivity;

import dagger.Component;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = LoginActivityModule.class)
public interface LoginActivityComponent {
    AppCompatActivity activity();
    LoginControl.LoginView view();
    void inject(LoginActivity activity);
}
