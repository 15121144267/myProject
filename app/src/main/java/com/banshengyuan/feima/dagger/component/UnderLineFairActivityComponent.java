package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.UnderLineFairActivityModule;
import com.banshengyuan.feima.view.PresenterControl.UnderLineFairControl;
import com.banshengyuan.feima.view.activity.UnderLineFairActivity;

import dagger.Component;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = UnderLineFairActivityModule.class)
public interface UnderLineFairActivityComponent {
    AppCompatActivity activity();
    UnderLineFairControl.UnderLineFairView view();
    void inject(UnderLineFairActivity activity);
}
