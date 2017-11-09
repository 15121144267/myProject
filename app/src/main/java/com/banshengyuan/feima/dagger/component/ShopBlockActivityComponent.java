package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.ShopBlockActivityModule;
import com.banshengyuan.feima.view.PresenterControl.ShopBlockControl;
import com.banshengyuan.feima.view.activity.ShopBlockActivity;

import dagger.Component;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ShopBlockActivityModule.class)
public interface ShopBlockActivityComponent {
    AppCompatActivity activity();
    ShopBlockControl.ShopBlockView view();
    void inject(ShopBlockActivity activity);
}
