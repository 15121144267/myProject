package com.dispatching.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.dagger.module.WorkSummaryActivityModule;
import com.dispatching.feima.view.activity.WorkSummaryActivity;

import dagger.Component;

/**
 * Created by helei on 2017/4/26.
 * WorkSummaryComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = WorkSummaryActivityModule.class)
public interface WorkSummaryComponent {
    AppCompatActivity activity();
    void inject(WorkSummaryActivity activity);
}
