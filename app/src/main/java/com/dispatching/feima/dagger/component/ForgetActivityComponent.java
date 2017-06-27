package com.dispatching.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.dagger.module.ForgetActivityModule;
import com.dispatching.feima.view.PresenterControl.ForgetControl;
import com.dispatching.feima.view.activity.ForgetActivity;

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
