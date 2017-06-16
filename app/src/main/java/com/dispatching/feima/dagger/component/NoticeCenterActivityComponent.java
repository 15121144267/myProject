package com.dispatching.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.dagger.module.NoticeCenterActivityModule;
import com.dispatching.feima.view.PresenterControl.NoticeCenterControl;
import com.dispatching.feima.view.activity.NoticeCenterActivity;

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
