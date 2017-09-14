package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.SignActivityModule;
import com.banshengyuan.feima.view.PresenterControl.SignControl;
import com.banshengyuan.feima.view.activity.SignActivity;

import dagger.Component;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = SignActivityModule.class)
public interface SignActivityComponent {
    AppCompatActivity activity();
    SignControl.SignView view();
    void inject(SignActivity activity);
}
