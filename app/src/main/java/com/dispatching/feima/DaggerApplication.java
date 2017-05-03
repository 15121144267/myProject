package com.dispatching.feima;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.dispatching.feima.dagger.component.ApplicationComponent;
import com.dispatching.feima.dagger.component.DaggerApplicationComponent;
import com.dispatching.feima.dagger.module.ApplicationModule;
import com.dispatching.feima.utils.Utils;

public class DaggerApplication extends Application {
    private ApplicationComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        mAppComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        mAppComponent.inject(this);
        Utils.init(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return mAppComponent;
    }
}
