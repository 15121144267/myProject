package com.dispatching.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.dagger.module.SignActivityModule;
import com.dispatching.feima.view.PresenterControl.SignControl;

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
    SignControl.PresenterSign getPresenterSign();
}
