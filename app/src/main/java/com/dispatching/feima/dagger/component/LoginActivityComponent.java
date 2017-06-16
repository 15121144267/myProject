package com.dispatching.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.dagger.module.LoginActivityModule;
import com.dispatching.feima.view.PresenterControl.LoginControl;

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
    LoginControl.PresenterLogin getPresenterLogin();
}
