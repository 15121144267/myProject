package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.OrderDetailActivityModule;
import com.banshengyuan.feima.view.PresenterControl.OrderDetailControl;
import com.banshengyuan.feima.view.activity.OrderDetailActivity;

import dagger.Component;

/**
 * Created by helei on 2017/4/26.
 * OrderDetailActivityComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = OrderDetailActivityModule.class)
public interface OrderDetailActivityComponent {
    AppCompatActivity activity();
    OrderDetailControl.OrderDetailView view();
    void inject(OrderDetailActivity activity);
}
