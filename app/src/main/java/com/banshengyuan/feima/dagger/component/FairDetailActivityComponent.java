package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.FairDetailActivityModule;
import com.banshengyuan.feima.view.PresenterControl.FairDetailControl;
import com.banshengyuan.feima.view.activity.FairDetailActivity;

import dagger.Component;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = FairDetailActivityModule.class)
public interface FairDetailActivityComponent {
    AppCompatActivity activity();
    FairDetailControl.FairDetailView view();
    void inject(FairDetailActivity activity);
}
