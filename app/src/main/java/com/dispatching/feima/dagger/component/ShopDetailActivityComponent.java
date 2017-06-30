package com.dispatching.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.dagger.module.ShopDetailActivityModule;
import com.dispatching.feima.view.PresenterControl.ShopDetailControl;
import com.dispatching.feima.view.activity.ShopDetailActivity;

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
    void inject(ShopDetailActivity Activity);
}
