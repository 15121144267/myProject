package com.dispatching.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.dagger.module.MyOrderActivityModule;
import com.dispatching.feima.view.PresenterControl.MyOrderControl;
import com.dispatching.feima.view.activity.MyOrderActivity;

import dagger.Component;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = MyOrderActivityModule.class)
public interface MyOrderActivityComponent {
    AppCompatActivity activity();
    MyOrderControl.MyOrderView view();
    void inject(MyOrderActivity activity);
}
