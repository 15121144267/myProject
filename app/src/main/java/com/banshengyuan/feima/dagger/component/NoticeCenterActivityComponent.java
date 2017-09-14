package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.NoticeCenterActivityModule;
import com.banshengyuan.feima.view.PresenterControl.NoticeCenterControl;
import com.banshengyuan.feima.view.activity.NoticeCenterActivity;

import dagger.Component;

/**
 * Created by helei on 2017/4/26.
 * NoticeCenterActivityComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = NoticeCenterActivityModule.class)
public interface NoticeCenterActivityComponent {
    AppCompatActivity activity();
    NoticeCenterControl.NoticeCenterView view();
    void inject(NoticeCenterActivity activity);
}
