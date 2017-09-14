package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.ForgetActivityModule;
import com.banshengyuan.feima.view.PresenterControl.ForgetControl;
import com.banshengyuan.feima.view.activity.ForgetActivity;

import dagger.Component;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ForgetActivityModule.class)
public interface ForgetActivityComponent {
    AppCompatActivity activity();
    ForgetControl.ForgetView view();
    void inject(ForgetActivity activity);
}
