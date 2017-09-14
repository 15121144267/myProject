package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.ClassifySearchActivityModule;
import com.banshengyuan.feima.view.PresenterControl.ClassifySearchControl;
import com.banshengyuan.feima.view.activity.ClassifySearchActivity;

import dagger.Component;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ClassifySearchActivityModule.class)
public interface ClassifySearchActivityComponent {
    AppCompatActivity activity();
    ClassifySearchControl.ClassifySearchView view();
    void inject(ClassifySearchActivity activity);
}
