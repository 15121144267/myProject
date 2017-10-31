package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.MainActivityModule;
import com.banshengyuan.feima.view.PresenterControl.MainControl;
import com.banshengyuan.feima.view.activity.MainActivity;

import dagger.Component;

/**
 * Created by helei on 2017/4/26.
 * MainActivityComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = MainActivityModule.class)
public interface MainActivityComponent {
    AppCompatActivity activity();
    MainControl.MainView view();
    void inject(MainActivity activity);
}
