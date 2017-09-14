package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.GoodsClassifyActivityModule;
import com.banshengyuan.feima.view.PresenterControl.GoodsClassifyControl;
import com.banshengyuan.feima.view.activity.GoodsClassifyActivity;

import dagger.Component;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = GoodsClassifyActivityModule.class)
public interface GoodsClassifyActivityComponent {
    AppCompatActivity activity();
    GoodsClassifyControl.GoodsClassifyView view();
    void inject(GoodsClassifyActivity activity);
}
