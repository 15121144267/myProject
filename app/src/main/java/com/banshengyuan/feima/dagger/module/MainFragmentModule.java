package com.banshengyuan.feima.dagger.module;


import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.view.PresenterControl.HotControl;
import com.banshengyuan.feima.view.PresenterControl.LoadDataView;
import com.banshengyuan.feima.view.PresenterControl.MagicMusicControl;
import com.banshengyuan.feima.view.PresenterControl.MainFairControl;
import com.banshengyuan.feima.view.PresenterControl.RecommendControl;
import com.banshengyuan.feima.view.PresenterControl.VistaControl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/5/3.
 * FragmentModule
 */
@Module
public class MainFragmentModule {
    private final AppCompatActivity activity;

    private RecommendControl.RecommendView mRecommendView;
    private MainFairControl.MainFairView mMainFairView;
    private HotControl.HotView mHotView;
    private VistaControl.VistaView mVistaView;
    private MagicMusicControl.MagicMusicView mMagicMusicView;

    public MainFragmentModule(LoadDataView view, AppCompatActivity activity) {
        this.activity = activity;
        if (view instanceof RecommendControl.RecommendView) {
            mRecommendView = (RecommendControl.RecommendView) view;
        } else if (view instanceof MainFairControl.MainFairView) {
            mMainFairView = (MainFairControl.MainFairView) view;
        } else if (view instanceof HotControl.HotView) {
            mHotView = (HotControl.HotView) view;
        } else if (view instanceof VistaControl.VistaView) {
            mVistaView = (VistaControl.VistaView) view;
        } else if (view instanceof MagicMusicControl.MagicMusicView) {
            mMagicMusicView = (MagicMusicControl.MagicMusicView) view;
        }
    }

    @Provides
    @PerActivity
    RecommendControl.RecommendView recommendView() {
        return this.mRecommendView;
    }

    @Provides
    @PerActivity
    MainFairControl.MainFairView mainFairView() {
        return this.mMainFairView;
    }

    @Provides
    @PerActivity
    HotControl.HotView hotView() {
        return this.mHotView;
    }

    @Provides
    @PerActivity
    VistaControl.VistaView vistaView() {
        return this.mVistaView;
    }

    @Provides
    @PerActivity
    MagicMusicControl.MagicMusicView magicMusicView() {
        return this.mMagicMusicView;
    }
}
