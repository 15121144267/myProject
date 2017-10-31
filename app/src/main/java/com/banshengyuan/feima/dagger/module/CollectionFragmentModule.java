package com.banshengyuan.feima.dagger.module;


import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.view.PresenterControl.CollectionBlockControl;
import com.banshengyuan.feima.view.PresenterControl.CollectionFairControl;
import com.banshengyuan.feima.view.PresenterControl.CollectionHotControl;
import com.banshengyuan.feima.view.PresenterControl.CollectionProductControl;
import com.banshengyuan.feima.view.PresenterControl.CollectionShopControl;
import com.banshengyuan.feima.view.PresenterControl.LoadDataView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/5/3.
 * FragmentModule
 */
@Module
public class CollectionFragmentModule {
    private final AppCompatActivity activity;

    private CollectionFairControl.CollectionFairView mCollectionFairView;
    private CollectionProductControl.CollectionProductView mCollectionProductView;
    private CollectionHotControl.CollectionHotView mCollectionHotView;
    private CollectionShopControl.CollectionShopView mCollectionShopView;
    private CollectionBlockControl.CollectionBlockView mCollectionBlockView;

    public CollectionFragmentModule(LoadDataView view, AppCompatActivity activity) {
        this.activity = activity;
        if (view instanceof CollectionFairControl.CollectionFairView) {
            mCollectionFairView = (CollectionFairControl.CollectionFairView) view;
        } else if (view instanceof CollectionProductControl.CollectionProductView) {
            mCollectionProductView = (CollectionProductControl.CollectionProductView) view;
        } else if (view instanceof CollectionHotControl.CollectionHotView) {
            mCollectionHotView = (CollectionHotControl.CollectionHotView) view;
        } else if (view instanceof CollectionShopControl.CollectionShopView) {
            mCollectionShopView = (CollectionShopControl.CollectionShopView) view;
        } else if (view instanceof CollectionBlockControl.CollectionBlockView) {
            mCollectionBlockView = (CollectionBlockControl.CollectionBlockView) view;
        }
    }


    @Provides
    @PerActivity
    CollectionFairControl.CollectionFairView collectionFairView() {
        return this.mCollectionFairView;
    }

    @Provides
    @PerActivity
    CollectionProductControl.CollectionProductView collectionProductView() {
        return this.mCollectionProductView;
    }

    @Provides
    @PerActivity
    CollectionHotControl.CollectionHotView collectionHotView() {
        return this.mCollectionHotView;
    }

    @Provides
    @PerActivity
    CollectionShopControl.CollectionShopView collectionShopView() {
        return this.mCollectionShopView;
    }

    @Provides
    @PerActivity
    CollectionBlockControl.CollectionBlockView collectionBlockView() {
        return this.mCollectionBlockView;
    }
}
