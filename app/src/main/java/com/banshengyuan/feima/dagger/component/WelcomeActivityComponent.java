package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.WelcomeActivityModule;
import com.banshengyuan.feima.view.PresenterControl.WelcomeControl;
import com.banshengyuan.feima.view.activity.WelcomeActivity;

import dagger.Component;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = WelcomeActivityModule.class)
public interface WelcomeActivityComponent {
    AppCompatActivity activity();
    WelcomeControl.WelcomeView view();
    WelcomeControl.PresenterWelcome getPresenterWelcome();
    void inject(WelcomeActivity activity);
}
