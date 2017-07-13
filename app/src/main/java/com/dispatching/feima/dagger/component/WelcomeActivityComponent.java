package com.dispatching.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.dagger.module.WelcomeActivityModule;
import com.dispatching.feima.view.PresenterControl.WelcomeControl;
import com.dispatching.feima.view.activity.WelcomeActivity;

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
