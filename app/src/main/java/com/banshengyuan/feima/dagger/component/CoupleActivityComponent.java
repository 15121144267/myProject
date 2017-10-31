package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.CoupleActivityModule;
import com.banshengyuan.feima.view.PresenterControl.CoupleControl;
import com.banshengyuan.feima.view.activity.CoupleActivity;

import dagger.Component;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = CoupleActivityModule.class)
public interface CoupleActivityComponent {
    AppCompatActivity activity();
    CoupleControl.CoupleView view();
    void inject(CoupleActivity activity);
}
