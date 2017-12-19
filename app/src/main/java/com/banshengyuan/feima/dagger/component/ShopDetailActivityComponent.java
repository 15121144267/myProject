package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.ShopDetailActivityModule;
import com.banshengyuan.feima.view.PresenterControl.ShopDetailControl;
import com.banshengyuan.feima.view.activity.GoodsCommentActivity;

import dagger.Component;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ShopDetailActivityModule.class)
public interface ShopDetailActivityComponent {
    AppCompatActivity activity();
    ShopDetailControl.ShopDetailView view();
    void inject(GoodsCommentActivity Activity);
}
