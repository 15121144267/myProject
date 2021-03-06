package com.dispatching.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.dagger.module.BaseActivityModule;

import dagger.Component;

/**
 * Created by helei on 2017/4/26.
 * BaseActivityComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = BaseActivityModule.class)
public interface BaseActivityComponent {
    AppCompatActivity activity();

}
