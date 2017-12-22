package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.FinalPayActivityModule;
import com.banshengyuan.feima.view.PresenterControl.FinalPayControl;
import com.banshengyuan.feima.view.activity.FinalPayActivity;

import dagger.Component;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = FinalPayActivityModule.class)
public interface FinalPayActivityComponent {
    AppCompatActivity activity();
    FinalPayControl.FinalPayView view();
    void inject(FinalPayActivity activity);
}
