package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.MyOrderActivityModule;
import com.banshengyuan.feima.view.PresenterControl.MyOrderControl;
import com.banshengyuan.feima.view.activity.MyOrderActivity;

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
