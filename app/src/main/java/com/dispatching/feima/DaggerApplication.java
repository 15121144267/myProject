package com.dispatching.feima;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.dispatching.feima.dagger.component.ApplicationComponent;
import com.dispatching.feima.dagger.component.DaggerApplicationComponent;
import com.dispatching.feima.dagger.module.ApplicationModule;
import com.dispatching.feima.utils.SharePreferenceUtil;
import com.dispatching.feima.utils.Utils;
import com.google.gson.Gson;

import javax.inject.Inject;

public class DaggerApplication extends Application {
    private ApplicationComponent mAppComponent;
    private AMapLocation aMapLocation;
    @Inject
    SharePreferenceUtil mSharePreferenceUtil;

    @Inject
    AMapLocationClient mAMapLocationClient;
    @Inject
    Gson mGson;
    @Inject
    AMapLocationClientOption mAMapLocationClientOption;

//    private String mUId;

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        mAppComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        mAppComponent.inject(this);
        Utils.init(this);

        /*//本地服务
        mUId = mSharePreferenceUtil.getStringValue(SpConstant.USER_ID);
        if (!TextUtils.isEmpty(mUId)) {
            startService(CustomerService.newIntent(getApplicationContext()));
        }*/
        mAMapLocationClient.setLocationOption(mAMapLocationClientOption);
        mAMapLocationClient.startLocation();
    }

    public ApplicationComponent getApplicationComponent() {
        return mAppComponent;
    }

    public void transformLocation(AMapLocation aMapLocation) {
        this.aMapLocation = aMapLocation;
        /*if (TextUtils.isEmpty(mUId)) {
            mUId = mSharePreferenceUtil.getStringValue(SpConstant.USER_ID);
        } else {
            Intent intent = new Intent(CustomerService.ACTION);
            intent.setClass(getApplicationContext(), CustomerService.class);
            intent.putExtra(IntentConstant.LONGITUDE, aMapLocation.getLongitude());
            intent.putExtra(IntentConstant.LATITUDE, aMapLocation.getLatitude());
            startService(intent);
        }*/
    }

    public AMapLocation getMapLocation() {
        return aMapLocation;
    }
}
