package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.WorkSummaryActivityModule;
import com.banshengyuan.feima.view.PresenterControl.WorkSummaryControl;
import com.banshengyuan.feima.view.activity.WorkSummaryActivity;

import dagger.Component;

/**
 * Created by helei on 2017/4/26.
 * WorkSummaryComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = WorkSummaryActivityModule.class)
public interface WorkSummaryComponent {
    AppCompatActivity activity();
    WorkSummaryControl.WorkSummaryView view();
    void inject(WorkSummaryActivity activity);
}
