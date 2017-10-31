package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.CollectionActivityModule;
import com.banshengyuan.feima.view.PresenterControl.CollectionControl;
import com.banshengyuan.feima.view.activity.MyCollectionActivity;

import dagger.Component;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = CollectionActivityModule.class)
public interface CollectionActivityComponent {
    AppCompatActivity activity();
    CollectionControl.CollectionView view();
    void inject(MyCollectionActivity activity);
}
