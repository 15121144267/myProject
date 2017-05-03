package com.dispatching.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.dagger.module.OrderDetailActivityModule;
import com.dispatching.feima.view.activity.OrderDetailActivity;

import dagger.Component;

/**
 * Created by helei on 2017/4/26.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = OrderDetailActivityModule.class)
public interface OrderDetailActivityComponent {
    AppCompatActivity activity();

    void inject(OrderDetailActivity activity);
}
