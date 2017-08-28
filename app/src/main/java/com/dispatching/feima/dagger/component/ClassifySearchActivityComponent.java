package com.dispatching.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.dagger.module.ClassifySearchActivityModule;
import com.dispatching.feima.view.PresenterControl.ClassifySearchControl;
import com.dispatching.feima.view.activity.ClassifySearchActivity;

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
