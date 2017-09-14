package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.ShoppingCardActivityModule;
import com.banshengyuan.feima.view.PresenterControl.ShoppingCardControl;
import com.banshengyuan.feima.view.activity.ShoppingCardActivity;

import dagger.Component;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ShoppingCardActivityModule.class)
public interface ShoppingCardActivityComponent {
    AppCompatActivity activity();
    ShoppingCardControl.ShoppingCardView view();
    void inject(ShoppingCardActivity activity);
}
