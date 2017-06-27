package com.dispatching.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.dagger.module.MainActivityModule;
import com.dispatching.feima.view.PresenterControl.MainControl;
import com.dispatching.feima.view.activity.MainActivity;

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
//    void inject(SendingOrderFragment sendingOrderFragment);
//    void inject(CompletedOrderFragment completedOrderFragment);
}
