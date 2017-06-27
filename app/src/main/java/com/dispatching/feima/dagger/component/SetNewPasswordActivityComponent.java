package com.dispatching.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.dagger.module.SetNewPasswordActivityModule;
import com.dispatching.feima.view.PresenterControl.SetNewPasswordControl;
import com.dispatching.feima.view.activity.SetNewPasswordActivity;

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
