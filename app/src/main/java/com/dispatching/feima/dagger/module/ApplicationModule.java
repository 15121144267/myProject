/**
 * Copyright (C) 2015 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.dispatching.feima.dagger.module;

import android.content.Context;

import com.dispatching.feima.DaggerApplication;
import com.dispatching.feima.database.DbHelper;
import com.dispatching.feima.entity.BuProcessor;
import com.dispatching.feima.gen.DaoMaster;
import com.dispatching.feima.gen.DaoSession;
import com.dispatching.feima.utils.SharePreferenceUtil;
import com.dispatching.feima.view.model.ModelTransform;
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
    DbHelper provideDbHelper(Context context) {
        return new DbHelper(context);
    }

    @Provides
    @Singleton
    DaoSession provideDaoSession (DbHelper dbHelper) {
        return new DaoMaster(dbHelper.getWritableDatabase()).newSession();
    }

    @Provides
    @Singleton
    ModelTransform provideModelTransform ( ) {
        return new ModelTransform();
    }

    @Singleton
    @Provides
    Gson provideGson(){return new GsonBuilder().create();}

    @Provides
    @Singleton
    BuProcessor provideBuProcessor(Context arg1, SharePreferenceUtil arg2) {
        return new BuProcessor(arg1,arg2);
    }
}
