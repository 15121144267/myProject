package com.dispatching.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.dagger.module.PayActivityModule;
import com.dispatching.feima.view.PresenterControl.PayControl;
import com.dispatching.feima.view.activity.PayActivity;

import dagger.Component;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = PayActivityModule.class)
public interface PayActivityComponent {
    AppCompatActivity activity();
    PayControl.PayView view();
    void inject(PayActivity activity);
}
