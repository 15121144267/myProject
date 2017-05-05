package com.dispatching.feima;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;

import com.dispatching.feima.dagger.component.ApplicationComponent;
import com.dispatching.feima.dagger.component.DaggerApplicationComponent;
import com.dispatching.feima.dagger.module.ApplicationModule;
import com.dispatching.feima.entity.SpConstant;
import com.dispatching.feima.service.CustomerService;
import com.dispatching.feima.utils.SharePreferenceUtil;
import com.dispatching.feima.utils.Utils;

import javax.inject.Inject;

public class DaggerApplication extends Application {
    private ApplicationComponent mAppComponent;
    @Inject
    SharePreferenceUtil mSharePreferenceUtil;
    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        mAppComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        mAppComponent.inject(this);
        Utils.init(this);

        //本地服务
        String userName  = mSharePreferenceUtil.getStringValue(SpConstant.USER_NAME);
        if(!TextUtils.isEmpty(userName)){
            startService(CustomerService.newIntent(getApplicationContext()));
        }

    }

    public ApplicationComponent getApplicationComponent() {
        return mAppComponent;
    }
}
