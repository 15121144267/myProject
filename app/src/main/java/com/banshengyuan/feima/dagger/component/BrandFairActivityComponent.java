package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.BrandFairActivityModule;
import com.banshengyuan.feima.view.PresenterControl.BrandFairControl;
import com.banshengyuan.feima.view.activity.BrandFairActivity;

import dagger.Component;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = BrandFairActivityModule.class)
public interface BrandFairActivityComponent {
    AppCompatActivity activity();
    BrandFairControl.BrandFairView view();
    void inject(BrandFairActivity brandFairActivity);
}
