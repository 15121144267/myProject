package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.ReductionPayActivityModule;
import com.banshengyuan.feima.view.PresenterControl.ReductionPayControl;
import com.banshengyuan.feima.view.activity.ReductionPayActivity;

import dagger.Component;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ReductionPayActivityModule.class)
public interface ReductionPayActivityComponent {
    AppCompatActivity activity();
    ReductionPayControl.ReductionPayView view();
    void inject(ReductionPayActivity activity);
}
