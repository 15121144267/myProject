package com.dispatching.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.dagger.module.GoodsDetailActivityModule;
import com.dispatching.feima.view.PresenterControl.GoodsDetailControl;
import com.dispatching.feima.view.activity.GoodDetailActivity;

import dagger.Component;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = GoodsDetailActivityModule.class)
public interface GoodsDetailActivityComponent {
    AppCompatActivity activity();
    GoodsDetailControl.GoodsDetailView view();
    void inject(GoodDetailActivity Activity);
}
