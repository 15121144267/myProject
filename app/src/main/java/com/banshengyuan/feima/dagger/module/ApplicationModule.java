
package com.banshengyuan.feima.dagger.module;

import android.content.Context;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.banshengyuan.feima.DaggerApplication;
import com.banshengyuan.feima.database.DbHelper;
import com.banshengyuan.feima.entity.BuProcessor;
import com.banshengyuan.feima.gen.DaoMaster;
import com.banshengyuan.feima.gen.DaoSession;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.banshengyuan.feima.listener.MyLocationListener;
import com.banshengyuan.feima.utils.SharePreferenceUtil;
import com.banshengyuan.feima.view.model.ModelTransform;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
    private final DaggerApplication application;

    public ApplicationModule(DaggerApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    SharePreferenceUtil provideSharePreferenceUtil(Context ctx) {
        return new SharePreferenceUtil(ctx);
    }

    @Provides
    @Singleton
    ImageLoaderHelper provideImageLoaderHelper() {
        return new ImageLoaderHelper();
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(Context context) {
        return new DbHelper(context);
    }

    @Provides
    @Singleton
    DaoSession provideDaoSession(DbHelper dbHelper) {
        return new DaoMaster(dbHelper.getWritableDatabase()).newSession();
    }

    @Provides
    @Singleton
    ModelTransform provideModelTransform() {
        return new ModelTransform();
    }

    @Singleton
    @Provides
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    BuProcessor provideBuProcessor() {
        return new BuProcessor();
    }

    @Provides
    @Singleton
    AMapLocationClient provideAMapLocationClient() {
        AMapLocationListener mLocationListener = new MyLocationListener(application);
        AMapLocationClient client = new AMapLocationClient(application);
        client.setLocationListener(mLocationListener);
        return client;
    }


    @Provides
    @Singleton
    AMapLocationClientOption AMapLocationClientOptionProvide() {
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setNeedAddress(true);
        option.setLocationCacheEnable(true);
        option.setHttpTimeOut(35000);
        option.setWifiScan(true);
        option.setInterval(30000);
        return option;
    }
}
