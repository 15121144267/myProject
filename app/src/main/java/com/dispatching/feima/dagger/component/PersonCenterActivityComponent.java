package com.dispatching.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.dagger.module.PersonCenterActivityModule;
import com.dispatching.feima.view.PresenterControl.PersonCenterControl;
import com.dispatching.feima.view.activity.PersonCenterActivity;

import dagger.Component;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = PersonCenterActivityModule.class)
public interface PersonCenterActivityComponent {
    AppCompatActivity activity();
    PersonCenterControl.PersonCenterView view();
    void inject(PersonCenterActivity activity);
}
