package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.ProductListActivityModule;
import com.banshengyuan.feima.view.PresenterControl.ProductListControl;
import com.banshengyuan.feima.view.activity.ProductListActivity;

import dagger.Component;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ProductListActivityModule.class)
public interface ProductListActivityComponent {
    AppCompatActivity activity();
    ProductListControl.ProductListView view();
    void inject(ProductListActivity activity);
}
