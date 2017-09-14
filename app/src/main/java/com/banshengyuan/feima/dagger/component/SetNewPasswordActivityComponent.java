package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.SetNewPasswordActivityModule;
import com.banshengyuan.feima.view.PresenterControl.SetNewPasswordControl;
import com.banshengyuan.feima.view.activity.SetNewPasswordActivity;

import dagger.Component;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = SetNewPasswordActivityModule.class)
public interface SetNewPasswordActivityComponent {
    AppCompatActivity activity();
    SetNewPasswordControl.SetNewPasswordView view();
    void inject(SetNewPasswordActivity activity);
}
