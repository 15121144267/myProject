package com.banshengyuan.feima;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.banshengyuan.feima.dagger.component.ApplicationComponent;
import com.banshengyuan.feima.dagger.component.DaggerApplicationComponent;
import com.banshengyuan.feima.dagger.module.ApplicationModule;
import com.banshengyuan.feima.entity.BuProcessor;
import com.banshengyuan.feima.utils.SharePreferenceUtil;
import com.banshengyuan.feima.utils.Utils;
import com.google.gson.Gson;

import javax.inject.Inject;

public class DaggerApplication extends Application {
    private ApplicationComponent mAppComponent;
    private AMapLocation aMapLocation;
    @Inject
    SharePreferenceUtil mSharePreferenceUtil;
    @Inject
    BuProcessor mBuProcessor;
    @Inject
    AMapLocationClient mAMapLocationClient;
    @Inject
    Gson mGson;
    @Inject
    AMapLocationClientOption mAMapLocationClientOption;

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        mAppComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        mAppComponent.inject(this);
        Utils.init(this);
        mBuProcessor.reSetUserData();
        mAMapLocationClient.setLocationOption(mAMapLocationClientOption);
        mAMapLocationClient.startLocation();
    }

    public ApplicationComponent getApplicationComponent() {
        return mAppComponent;
    }

    public void transformLocation(AMapLocation aMapLocation) {
        this.aMapLocation = aMapLocation;
    }

    public AMapLocation getMapLocation() {
        return aMapLocation;
    }
}
