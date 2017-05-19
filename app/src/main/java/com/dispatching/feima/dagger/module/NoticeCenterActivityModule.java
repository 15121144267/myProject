package com.dispatching.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.gen.DaoSession;
import com.dispatching.feima.view.PresenterControl.NoticeCenterControl;
import com.dispatching.feima.view.PresenterImpl.PresenterNoticeCenterImpl;
import com.dispatching.feima.view.model.NoticeCenterModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * NoticeCenterActivityModule
 */
@Module
public class NoticeCenterActivityModule {
    private final AppCompatActivity activity;

    public NoticeCenterActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    AppCompatActivity activity() {
        return this.activity;
    }

    @Provides
    @PerActivity
    NoticeCenterModel provideNoticeCenterModel(DaoSession daoSession) {
        return new NoticeCenterModel(daoSession.getOrderNoticeDao());
    }

    @Provides
    @PerActivity
    NoticeCenterControl.PresenterNoticeCenter providePresenterNoticeCenter(PresenterNoticeCenterImpl presenter) {
        return presenter;
    }
}
